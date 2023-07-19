package project.linkarchive.backend.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.linkarchive.backend.advice.exception.custom.*;
import project.linkarchive.backend.badword.BadWordFiltering;
import project.linkarchive.backend.profileImage.domain.ProfileImage;
import project.linkarchive.backend.profileImage.repository.ProfileImageRepository;
import project.linkarchive.backend.profileImage.response.ProfileImageResponse;
import project.linkarchive.backend.s3.S3Uploader;
import project.linkarchive.backend.user.domain.Relationship;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.RelationshipRepository;
import project.linkarchive.backend.user.repository.UserRepository;
import project.linkarchive.backend.user.request.UpdateNicknameRequest;
import project.linkarchive.backend.user.request.UpdateProfileRequest;
import project.linkarchive.backend.user.response.UpdateNicknameResponse;
import project.linkarchive.backend.user.response.UpdateProfileResponse;

import java.io.IOException;

import static project.linkarchive.backend.advice.data.DataConstants.*;
import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.*;

@Service
@Transactional
public class UserApiService {

    private final S3Uploader s3Uploader;
    private final BadWordFiltering badWordFiltering;
    private final UserRepository userRepository;
    private final ProfileImageRepository userProfileImageRepository;
    private final RelationshipRepository relationshipRepository;

    @Value("${cloud.aws.s3.default-image}")
    private String defaultImage;

    public UserApiService(BadWordFiltering badWordFiltering, S3Uploader s3Uploader, UserRepository userRepository, ProfileImageRepository userProfileImageRepository, RelationshipRepository relationshipRepository) {
        this.badWordFiltering = badWordFiltering;
        this.s3Uploader = s3Uploader;
        this.userRepository = userRepository;
        this.userProfileImageRepository = userProfileImageRepository;
        this.relationshipRepository = relationshipRepository;
    }

    public UpdateNicknameResponse updateUserNickName(UpdateNicknameRequest request, Long userId) {
        validateNickname(request.getNickname());

        User user = getUserById(userId);
        existUserByNickname(request.getNickname(), user);

        user.updateNickName(request);

        return new UpdateNicknameResponse(user.getNickname());
    }

    public UpdateProfileResponse updateUserProfile(UpdateProfileRequest request, Long userId) {
        validateNickname(request.getNickname());
        validateIntroduce(request.getIntroduce());

        User user = getUserById(userId);
        existUserByNickname(request.getNickname(), user);

        user.updateProfile(request);

        return new UpdateProfileResponse(user);
    }

    public ProfileImageResponse updateProfileImage(MultipartFile image, Long userId) throws IOException {
        ProfileImage profileImage = getProfileImageByUserId(userId);

        validateNotEmptyFile(image);
        validateContentType(image.getContentType());

        String storedFileName = s3Uploader.upload(image);

        if (!profileImage.getProfileImageFilename().equals(defaultImage)) {
            s3Uploader.deleteFile(profileImage.getProfileImageFilename());
        }

        profileImage.updateProfileImage(storedFileName);

        String profileImageUrl = s3Uploader.generatePresignedProfileImageUrl(
                        storedFileName,
                        IMAGE_EXPIRATION_TIME)
                .toString();

        return new ProfileImageResponse(profileImageUrl);
    }

    public void checkNickName(String nickname) {
        validateNickname(nickname);
        validateUserByNickname(nickname);
    }

    public void followUser(Long followerId, Long followeeId) {
        User follower = findUserById(followerId);
        User followee = findUserById(followeeId);

        checkFollowStatus(follower.getId(), followee.getId());

        Relationship relationship = Relationship.build(follower.getId(), followee.getId());
        relationshipRepository.save(relationship);

        userRepository.increaseFollowingCount(follower.getId());
        userRepository.increaseFollowerCount(followee.getId());
    }

    public void unfollowUser(Long followerId, Long followeeId) {
        User follower = findUserById(followerId);
        User followee = findUserById(followeeId);

        Relationship relationship = checkUnFollowStatus(follower.getId(), followee.getId());

        relationshipRepository.delete(relationship);

        userRepository.decreaseFollowingCount(follower.getId());
        userRepository.decreaseFollowerCount(followee.getId());
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }

    private ProfileImage getProfileImageByUserId(Long userId) {
        return userProfileImageRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_PROFILE_IMAGE));
    }

    private void validateUserByNickname(String nickname) {
        if (userRepository.existsUserByNickname(nickname)) {
            throw new AlreadyExistException(ALREADY_EXIST_NICKNAME);
        }
    }

    private void validateNickname(String nickname) {
        validateNicknamePattern(nickname);
        validateNicknameLength(nickname);
        validateWordFiltering(nickname);
    }

    private void validateNicknamePattern(String nickname) {
        if (!PATTERN_REGAX.matcher(nickname).matches()) {
            throw new NotAcceptableException(INVALID_NICKNAME);
        }
    }

    private void validateNicknameLength(String nickname) {
        boolean isTooLong = isTooLong(nickname, MAXIMUM_NICKNAME_LENGTH);
        boolean isTooShort = isTooShort(nickname, MINIMUM_NICKNAME_LENGTH);
        if (isTooLong || isTooShort) {
            throw new LengthRequiredException(LENGTH_REQUIRED_NICKNAME);
        }
    }

    private void validateIntroduce(String introduce) {
        validateIntroduceLength(introduce);
        validateWordFiltering(introduce);
    }

    private void validateIntroduceLength(String introduce) {
        boolean isIntroduceTooLong = isTooLong(introduce, MAXIMUM_INTRODUCE_LENGTH);
        if (isIntroduceTooLong) {
            throw new LengthRequiredException(LENGTH_REQUIRED_INTRODUCE);
        }
    }

    private void validateWordFiltering(String word) {
        if (badWordFiltering.filter(word)) {
            throw new InvalidException(INVALID_BAD_WORD);
        }
    }

    private boolean isTooLong(String value, int maxLength) {
        return value.length() > maxLength;
    }

    private boolean isTooShort(String value, int minLength) {
        return value.length() < minLength;
    }

    private void existUserByNickname(String nickname, User user) {
        if (userRepository.existsUserByNickname(nickname) && !user.getNickname().equals(nickname)
        ) {
            throw new AlreadyExistException(ALREADY_EXIST_NICKNAME);
        }
    }

    private void validateNotEmptyFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new NotAcceptableException(EMPTY_UPLOAD_FILE);
        }
    }

    private void validateContentType(String contentType) {
        if (contentType != null && !contentType.matches(IMAGE_CONTENT_TYPE)) {
            throw new NotAcceptableException(NOT_ACCEPTABLE_CONTENT_TYPE);
        }
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }

    private void checkFollowStatus(Long followerId, Long followeeId){
        relationshipRepository.findByFollowerAndFollowee(followerId, followeeId)
                .ifPresent(i -> {
                    throw new AlreadyExistException(ALREADY_FOLLOWED);
                });
    }

    private Relationship checkUnFollowStatus(Long followerId, Long followeeId){
        Relationship relationship = relationshipRepository.findByFollowerAndFollowee(followerId, followeeId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_FOLLOW_STATUS));
        return relationship;
    }
}