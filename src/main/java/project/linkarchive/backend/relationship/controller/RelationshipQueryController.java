package project.linkarchive.backend.relationship.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.relationship.response.FollowListResponse;
import project.linkarchive.backend.relationship.response.FollowResponse;
import project.linkarchive.backend.relationship.service.RelationshipQueryService;
import project.linkarchive.backend.security.AuthInfo;

import java.util.List;

@RestController
public class RelationshipQueryController {
    private final RelationshipQueryService relationshipQueryService;

    public RelationshipQueryController(RelationshipQueryService relationshipQueryService) {
        this.relationshipQueryService = relationshipQueryService;
    }

    @GetMapping("/follower-list/user/{userId}")
    public ResponseEntity<FollowListResponse> getFollowerList(
            @PathVariable("userId") Long userId,
            @Nullable AuthInfo authInfo
    ) {
        FollowListResponse followListResponse = relationshipQueryService.getFollowerList(userId, authInfo);
        return ResponseEntity.ok(followListResponse);
    }

    @GetMapping("/following-list/user/{userId}")
    public ResponseEntity<FollowListResponse> getFollowingList(
            @PathVariable("userId") Long userId,
            @Nullable AuthInfo authInfo
    ) {
        FollowListResponse followListResponse = relationshipQueryService.getFollowingList(userId, authInfo);
        return ResponseEntity.ok(followListResponse);
    }
}
