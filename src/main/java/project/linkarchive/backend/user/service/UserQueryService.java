package project.linkarchive.backend.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.relationship.repository.RelationshipRepository;
import project.linkarchive.backend.s3.S3Uploader;
import project.linkarchive.backend.security.AuthInfo;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.UserRepository;
import project.linkarchive.backend.user.response.MyProfileResponse;
import project.linkarchive.backend.user.response.UserProfileResponse;

import static project.linkarchive.backend.advice.data.DataConstants.IMAGE_EXPIRATION_TIME;
import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.NOT_FOUND_USER;

@Service
@Transactional(readOnly = true)
public class UserQueryService {

    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;
    private final RelationshipRepository relationshipRepository;

    public UserQueryService(UserRepository userRepository, S3Uploader s3Uploader, RelationshipRepository relationshipRepository) {
        this.userRepository = userRepository;
        this.s3Uploader = s3Uploader;
        this.relationshipRepository = relationshipRepository;
    }

    public MyProfileResponse getMyProfile(Long userId) {
        User user = getUserById(userId);
        String profileImageUrl = generateProfileImageUrl(user.getProfileImage().getProfileImageFilename());

        return new MyProfileResponse(user, profileImageUrl);
    }

    public UserProfileResponse getUserProfile(String nickname, AuthInfo authInfo) {
        User user = getUserByNickname(nickname);
        String profileImageUrl = generateProfileImageUrl(user.getProfileImage().getProfileImageFilename());

        Long loginUserId = authInfo != null ? authInfo.getId() : null;
        if (loginUserId == null) {
            return new UserProfileResponse(user, profileImageUrl, false);
        }

        Boolean isFollow = isFollowing(user.getId(), authInfo.getId());
        return new UserProfileResponse(user, profileImageUrl, isFollow);
    }

    public String generateProfileImageUrl(String profileImageFilename) {
        return s3Uploader.generatePresignedProfileImageUrl(
                        profileImageFilename,
                        IMAGE_EXPIRATION_TIME)
                .toString();
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }

    private User getUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }

    private Boolean isFollowing(Long followeeId, Long followerId) {
        return relationshipRepository.existsByFolloweeIdAndFollowerId(followeeId, followerId);
    }

}