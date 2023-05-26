package project.linkarchive.backend.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.custom.AlreadyExistException;
import project.linkarchive.backend.advice.exception.custom.LengthRequiredException;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.s3.S3Uploader;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.UserRepository;
import project.linkarchive.backend.user.request.NickNameRequest;
import project.linkarchive.backend.user.response.ProfileResponse;

import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.*;

@Service
@Transactional(readOnly = true)
public class UserQueryService {

    public static final int MINIMUM_NICKNAME_LENGTH = 2;
    public static final int MAXIMUM_NICKNAME_LENGTH = 16;
    public final static int EXPIRATION_TIME_IN_MINUTES = 1000 * 60 * 60;

    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;

    public UserQueryService(UserRepository userRepository, S3Uploader s3Uploader) {
        this.userRepository = userRepository;
        this.s3Uploader = s3Uploader;
    }

    public ProfileResponse getUserProfile(Long userId) {
        User user = checkUserId(userId);
        String profileImageUrl = s3Uploader.generatePresignedProfileImageUrl(
                        user.getProfileImage().getProfileImageFilename(),
                        EXPIRATION_TIME_IN_MINUTES)
                .toString();

        return new ProfileResponse(user, profileImageUrl);
    }

    public void validationNickName(NickNameRequest request) {
        validationNickNameLength(request);

        userRepository.findByNickname(request.getNickname()).orElseThrow(
                () -> new AlreadyExistException(ALREADY_EXIST_NICKNAME)
        );
    }

    private User checkUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
        return user;
    }

    private void validationNickNameLength(NickNameRequest request) {
        boolean isTooLong = request.getNickname().length() > MAXIMUM_NICKNAME_LENGTH;
        boolean isTooShort = request.getNickname().length() < MINIMUM_NICKNAME_LENGTH;
        if (isTooLong || isTooShort) {
            throw new LengthRequiredException(LENGTH_REQUIRED_NICKNAME);
        }
    }

}