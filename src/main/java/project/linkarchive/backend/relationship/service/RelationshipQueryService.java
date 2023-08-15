package project.linkarchive.backend.relationship.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.ExceptionCodeConst;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.relationship.repository.RelationshipRepository;
import project.linkarchive.backend.relationship.response.FollowListResponse;
import project.linkarchive.backend.relationship.response.FollowResponse;
import project.linkarchive.backend.security.AuthInfo;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.UserRepository;
import project.linkarchive.backend.user.service.UserQueryService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class RelationshipQueryService {

    private final RelationshipRepository relationshipRepository;
    private final UserRepository userRepository;
    private final UserQueryService userQueryService;

    public RelationshipQueryService(RelationshipRepository relationshipRepository, UserRepository userRepository, UserQueryService userQueryService) {
        this.relationshipRepository = relationshipRepository;
        this.userRepository = userRepository;
        this.userQueryService = userQueryService;
    }

    public FollowListResponse getFollowerList(Long userId, AuthInfo authInfo) {
        findUserById(userId);

        List<User> followerList = relationshipRepository.findFollowerIdByFolloweeId(userId);

        List<FollowResponse> followResponses = new ArrayList<>();
        if (followerList == null || followerList.isEmpty()) {
            return new FollowListResponse(followResponses);
        }

        return getFollowResponses(authInfo, followerList);
    }

    public FollowListResponse getFollowingList(Long userId, AuthInfo authInfo) {
        findUserById(userId);

        List<User> followingList = relationshipRepository.findFolloweeIdByFollowerId(userId);

        List<FollowResponse> followResponses = new ArrayList<>();
        if (followingList == null || followingList.isEmpty()) {
            return new FollowListResponse(followResponses);
        }

        return getFollowResponses(authInfo, followingList);
    }

    private void findUserById(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ExceptionCodeConst.NOT_FOUND_USER));
    }

    private FollowListResponse getFollowResponses(AuthInfo authInfo, List<User> followList) {
        Long loginUserId = authInfo != null ? authInfo.getId() : null;

        List<FollowResponse> followResponses = new ArrayList<>();
        followList.forEach(user -> {
            String profileImageUrl = userQueryService.generateProfileImageUrl(user.getProfileImage().getFileName());

            Boolean isFollow = loginUserId != null && isFollowing(user.getId(), loginUserId);

            FollowResponse response = new FollowResponse(user, profileImageUrl, isFollow);
            followResponses.add(response);
        });

        return new FollowListResponse(followResponses);
    }

    private Boolean isFollowing(Long followeeId, Long followerId) {
        return relationshipRepository.existsByFolloweeIdAndFollowerId(followeeId, followerId);
    }
}
