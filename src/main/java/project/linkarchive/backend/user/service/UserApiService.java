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

import java.io.IOException;

import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.*;

@Service
@Transactional
public class UserApiService {

    public static final int MINIMUM_NICKNAME_LENGTH = 2;
    public static final int MAXIMUM_NICKNAME_LENGTH = 16;

    private final S3Uploader s3Uploader;
    private final UserRepository userRepository;
    private final UserProfileImageRepository userProfileImageRepository;

    public UserApiService(S3Uploader s3Uploader, UserRepository userRepository, UserProfileImageRepository userProfileImageRepository) {
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
        boolean isTooLong = request.getNickName().length() > MAXIMUM_NICKNAME_LENGTH;
        boolean isTooShort = request.getNickName().length() < MINIMUM_NICKNAME_LENGTH;
        if (isTooLong || isTooShort) {
            throw new LengthRequiredException(LENGTH_REQUIRED_NICKNAME);
        }
    }

    private void existUserNickName(UpdateNickNameRequest request, User user) {
        if (userRepository.existsUserByNickName(request.getNickName()) &&
                !user.getNickName().equals(request.getNickName())
        ) {
            throw new AlreadyExistException(ALREADY_EXIST_NICKNAME);
        }
    }

}