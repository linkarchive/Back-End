package project.linkarchive.backend.relationship.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.ExceptionCodeConst;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.relationship.repository.RelationshipRepository;
import project.linkarchive.backend.relationship.response.FollowInfoResponse;
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

    public List<FollowInfoResponse> getFollowerList(Long userId) {
        findUserById(userId);

        List<FollowInfoResponse> followerListResponse = new ArrayList<>();

        List<User> followerList = relationshipRepository.findFollowerIdByFolloweeId(userId);
        if (followerList.isEmpty()) {
            return followerListResponse;
        }

        followerList.forEach(follower -> {
            String profileImageUrl = userQueryService.generateProfileImageUrl(follower.getProfileImage().getProfileImageFilename());
            boolean isFollowing = isFollowing(follower.getId(), userId);
            FollowInfoResponse response = new FollowInfoResponse(follower, profileImageUrl, isFollowing);
            followerListResponse.add(response);
        });

        return followerListResponse;
    }



    public List<FollowInfoResponse> getFollowingList(Long userId) {
        findUserById(userId);

        List<FollowInfoResponse> followingListResponse = new ArrayList<>();

        List<User> followingList = relationshipRepository.findFolloweeIdByFollowerId(userId);

        if (followingList.isEmpty()) {
            return followingListResponse;
        }

        followingList.forEach(follower -> {
            String profileImageUrl = userQueryService.generateProfileImageUrl(follower.getProfileImage().getProfileImageFilename());
            boolean isFollowing = isFollowing(follower.getId(), userId);
            FollowInfoResponse response = new FollowInfoResponse(follower, profileImageUrl, isFollowing);
            followingListResponse.add(response);
        });

        return followingListResponse;
    }

    private void findUserById(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ExceptionCodeConst.NOT_FOUND_USER));
    }

    private boolean isFollowing(Long followeeId, Long followerId) {
        return relationshipRepository.existsByFolloweeIdAndFollowerId(followeeId, followerId);
    }
}
