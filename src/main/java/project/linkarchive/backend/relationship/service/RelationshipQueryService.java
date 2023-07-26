package project.linkarchive.backend.relationship.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.ExceptionCodeConst;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.hashtag.response.TagResponse;
import project.linkarchive.backend.link.domain.LinkHashTag;
import project.linkarchive.backend.link.response.linkarchive.UserArchiveResponse;
import project.linkarchive.backend.relationship.repository.RelationshipRepository;
import project.linkarchive.backend.relationship.response.FollowerListResponse;
import project.linkarchive.backend.relationship.response.FollowerResponse;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.UserRepository;
import project.linkarchive.backend.user.service.UserQueryService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<FollowerResponse> getFollowerList(Long userId) {
        findUserById(userId);

        List<FollowerResponse> followerListResponse = new ArrayList<>();

        List<User> userList = relationshipRepository.findFollowerIdByFolloweeId(userId);
        if (userList.isEmpty()) {
            return followerListResponse;
        }

        userList.forEach(user -> {
            String name = userQueryService.generateProfileImageUrl(user.getProfileImage().getProfileImageFilename());
            FollowerResponse response = new FollowerResponse(user, name);
            followerListResponse.add(response);
        });

        return followerListResponse;



    }

    private void findUserById(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ExceptionCodeConst.NOT_FOUND_USER));
    }
}
