package project.linkarchive.backend.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.linkarchive.backend.advice.exception.custom.*;
import project.linkarchive.backend.badword.BadWordFiltering;
import project.linkarchive.backend.profileImage.domain.ProfileImage;
import project.linkarchive.backend.profileImage.response.ProfileImageResponse;
import project.linkarchive.backend.s3.S3Uploader;
import project.linkarchive.backend.user.domain.User;
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

    @Value("${cloud.aws.s3.default-image}")
    private String DEFAULT_IMAGE;
    @Value("${cloud.aws.s3.url}")
    private String URL;

    public UserApiService(BadWordFiltering badWordFiltering, S3Uploader s3Uploader, UserRepository userRepository) {
        this.badWordFiltering = badWordFiltering;
        this.s3Uploader = s3Uploader;
        this.userRepository = userRepository;
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
        User user = getUserById(userId);

        validateNotEmptyFile(image);
        validateContentType(image.getContentType());

        String storedFileName = s3Uploader.upload(image);

        if (!user.getProfileImage().getProfileImageFilename().equals(DEFAULT_IMAGE)) {
            s3Uploader.deleteFile(user.getProfileImage().getProfileImageFilename());
        }

        ProfileImage profileImage = user.getProfileImage();
        profileImage.updateProfileImage(storedFileName);

        return new ProfileImageResponse(URL + profileImage.getProfileImageFilename());
    }

    public ProfileImageResponse defaultProfileImage(Long userId) {
        User user = getUserById(userId);

        ProfileImage profileImage = user.getProfileImage();
        profileImage.updateProfileImage(DEFAULT_IMAGE);

        return new ProfileImageResponse(URL + profileImage.getProfileImageFilename());
    }

    public void checkIfNicknameIsAvailable(String nickname) {
        validateNickname(nickname);
        checkIfNicknameExists(nickname);
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }

    private void checkIfNicknameExists(String nickname) {
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

}