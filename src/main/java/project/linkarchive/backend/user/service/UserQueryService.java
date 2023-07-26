package project.linkarchive.backend.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.s3.S3Uploader;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.UserRepository;
import project.linkarchive.backend.user.response.ProfileResponse;

import static project.linkarchive.backend.advice.data.DataConstants.IMAGE_EXPIRATION_TIME;
import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.NOT_FOUND_USER;

@Service
@Transactional(readOnly = true)
public class UserQueryService {

    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;

    public UserQueryService(UserRepository userRepository, S3Uploader s3Uploader) {
        this.userRepository = userRepository;
        this.s3Uploader = s3Uploader;
    }

    public ProfileResponse getMyProfile(Long userId) {
        User user = getUserById(userId);
        String profileImageUrl = generateProfileImageUrl(user.getProfileImage().getProfileImageFilename());

        return new ProfileResponse(user, profileImageUrl);
    }

    public ProfileResponse getUserProfile(String nickname) {
        User user = getUserByNickname(nickname);
        String profileImageUrl = generateProfileImageUrl(user.getProfileImage().getProfileImageFilename());

        return new ProfileResponse(user, profileImageUrl);
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }

    private User getUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }

    public String generateProfileImageUrl(String profileImageFilename) {
        return s3Uploader.generatePresignedProfileImageUrl(
                        profileImageFilename,
                        IMAGE_EXPIRATION_TIME)
                .toString();
    }

}