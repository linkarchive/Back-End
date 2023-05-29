package project.linkarchive.backend.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.linkarchive.backend.advice.exception.custom.AlreadyExistException;
import project.linkarchive.backend.advice.exception.custom.LengthRequiredException;
import project.linkarchive.backend.advice.exception.custom.NotAcceptableException;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.s3.S3Uploader;
import project.linkarchive.backend.user.domain.ProfileImage;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.UserProfileImageRepository;
import project.linkarchive.backend.user.repository.UserRepository;
import project.linkarchive.backend.user.request.NickNameRequest;
import project.linkarchive.backend.user.request.UpdateProfileRequest;
import project.linkarchive.backend.user.response.ProfileImageResponse;
import project.linkarchive.backend.user.response.UpdateNicknameResponse;
import project.linkarchive.backend.user.response.UpdateProfileResponse;

import java.io.IOException;

import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.*;

@Service
@Transactional
public class UserApiService {

    public static final int MINIMUM_NICKNAME_LENGTH = 2;
    public static final int MAXIMUM_NICKNAME_LENGTH = 16;
    public static final int MAXIMUM_INTRODUCE_LENGTH = 20;
    public final static int EXPIRATION_TIME_IN_MINUTES = 1000 * 60 * 60;
    public final static int S3_KEY = 3;

    private final S3Uploader s3Uploader;
    private final UserRepository userRepository;
    private final UserProfileImageRepository userProfileImageRepository;

    @Value("${cloud.aws.s3.default-image}")
    private String defaultImage;

    public UserApiService(S3Uploader s3Uploader, UserRepository userRepository, UserProfileImageRepository userProfileImageRepository) {
        this.s3Uploader = s3Uploader;
        this.userRepository = userRepository;
        this.userProfileImageRepository = userProfileImageRepository;
    }

    public UpdateNicknameResponse updateUserNickName(NickNameRequest request, Long userId) {
//        validationNickNameLength(request);

        User user = getUserById(userId);
//        existUserNickName(request, user);

        user.updateUserNickName(request);

        return new UpdateNicknameResponse(user.getNickname());
    }

    public UpdateProfileResponse updateUserProfile(UpdateProfileRequest request, Long userId) {
        validationProfileRequest(request);

        User user = getUserById(userId);
        existUserNickName(request, user);

        user.updateUserProfile(request);

        return new UpdateProfileResponse(user);
    }

    public ProfileImageResponse updateProfileImage(MultipartFile image, Long userId) throws IOException {
        getUserById(userId);
        ProfileImage profileImage = getProfileImageByUserId(userId);

        validateNotEmptyFile(image);
        validateContentType(image.getContentType());

        String storedFileName = s3Uploader.upload(image);
        storedFileName = extractKey(storedFileName);

        String oldProfileImageName = profileImage.getProfileImageFilename();
        if (!oldProfileImageName.equals(defaultImage)) {
            String oldFileKey = extractKey(oldProfileImageName);
            s3Uploader.deleteFile(oldFileKey);
        }

        profileImage.updateProfileImage(storedFileName);

        String profileImageUrl = s3Uploader.generatePresignedProfileImageUrl(storedFileName, EXPIRATION_TIME_IN_MINUTES).toString();
        return new ProfileImageResponse(profileImageUrl);
    }

    public void validationNickName(NickNameRequest request) {
        validationNickNameLength(request);

        if (userRepository.existsUserByNickname(request.getNickname())) {
            throw new AlreadyExistException(ALREADY_EXIST_NICKNAME);
        }
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }

    private ProfileImage getProfileImageByUserId(Long userId) {
        return userProfileImageRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_PROFILE_IMAGE));
    }

    private void validationNickNameLength(NickNameRequest request) {
        boolean isTooLong = request.getNickname().length() > MAXIMUM_NICKNAME_LENGTH;
        boolean isTooShort = request.getNickname().length() < MINIMUM_NICKNAME_LENGTH;
        if (isTooLong || isTooShort) {
            throw new LengthRequiredException(LENGTH_REQUIRED_NICKNAME);
        }
    }

    private void validationProfileRequest(UpdateProfileRequest request) {
        boolean isNicknameTooLong = request.getNickname().length() > MAXIMUM_NICKNAME_LENGTH;
        boolean isNicknameTooShort = request.getNickname().length() < MINIMUM_NICKNAME_LENGTH;
        if (isNicknameTooLong || isNicknameTooShort) {
            throw new LengthRequiredException(LENGTH_REQUIRED_NICKNAME);
        }

        boolean isIntroduceTooLong = request.getIntroduce().length() > MAXIMUM_INTRODUCE_LENGTH;
        if (isIntroduceTooLong) {
            throw new LengthRequiredException(LENGTH_REQUIRED_INTRODUCE);
        }
    }

    private void existUserNickName(NickNameRequest request, User user) {
        if (userRepository.existsUserByNickname(request.getNickname()) &&
                !user.getNickname().equals(request.getNickname())
        ) {
            throw new AlreadyExistException(ALREADY_EXIST_NICKNAME);
        }
    }

    private void existUserNickName(UpdateProfileRequest request, User user) {
        if (userRepository.existsUserByNickname(request.getNickname()) &&
                !user.getNickname().equals(request.getNickname())
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

        if (contentType != null) {
            if (!(contentType.contains("image/jpeg")
                    || contentType.contains("image/jpg")
                    || contentType.contains("image/png"))) {
                throw new NotAcceptableException(NOT_ACCEPTABLE_CONTENT_TYPE);
            }
        }
    }

    private String extractKey(String fileUrl) {
        String key = fileUrl.split("/")[S3_KEY];
        return key;
    }

}