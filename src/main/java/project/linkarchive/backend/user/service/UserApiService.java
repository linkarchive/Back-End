package project.linkarchive.backend.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.linkarchive.backend.advice.exception.custom.AlreadyExistException;
import project.linkarchive.backend.advice.exception.custom.LengthRequiredException;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.s3.S3Uploader;
import project.linkarchive.backend.user.domain.ProfileImage;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.UserProfileImageRepository;
import project.linkarchive.backend.user.repository.UserRepository;
import project.linkarchive.backend.user.request.UpdateNickNameRequest;
import project.linkarchive.backend.user.request.UpdateProfileRequest;
import project.linkarchive.backend.util.JwtUtil;

import java.io.IOException;

import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.*;

@Service
@Transactional
public class UserApiService {

    public static final int MINIMUM_NICKNAME_LENGTH = 2;
    public static final int MAXIMUM_NICKNAME_LENGTH = 16;
    public static final int MAXIMUM_INTRODUCE_LENGTH = 20;

    private final JwtUtil jwtUtil;
    private final S3Uploader s3Uploader;
    private final UserRepository userRepository;
    private final UserProfileImageRepository userProfileImageRepository;

    public UserApiService(JwtUtil jwtUtil, S3Uploader s3Uploader, UserRepository userRepository, UserProfileImageRepository userProfileImageRepository) {
        this.jwtUtil = jwtUtil;
        this.s3Uploader = s3Uploader;
        this.userRepository = userRepository;
        this.userProfileImageRepository = userProfileImageRepository;
    }

    public void updateUserNickName(UpdateNickNameRequest request, Long userId) {
        validationNickNameLength(request);

        User user = getUserById(userId);
        existUserNickName(request, user);

        user.updateUserNickName(request);
    }

    public void updateUserProfile(UpdateProfileRequest request, Long userId) {
        validationProfileRequest(request);

        User user = getUserById(userId);
        existUserNickName(request, user);

        user.updateUserProfile(request);
    }

    public void saveProfileImage(MultipartFile image, Long userId) throws IOException {
        getUserById(userId);
        ProfileImage profileImage = getProfileImageByUserId(userId);

        String storedFileName = s3Uploader.upload(image);
        profileImage.updateProfileImage(storedFileName);
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }

    private ProfileImage getProfileImageByUserId(Long userId) {
        return userProfileImageRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_PROFILE_IMAGE));
    }

    private void validationNickNameLength(UpdateNickNameRequest request) {
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

    private void existUserNickName(UpdateNickNameRequest request, User user) {
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

}