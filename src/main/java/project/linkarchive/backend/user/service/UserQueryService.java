package project.linkarchive.backend.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.s3.S3Uploader;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.UserRepository;
import project.linkarchive.backend.user.response.ProfileResponse;

import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.NOT_FOUND_USER;

@Service
@Transactional(readOnly = true)
public class UserQueryService {

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

    private User checkUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
        return user;
    }

}