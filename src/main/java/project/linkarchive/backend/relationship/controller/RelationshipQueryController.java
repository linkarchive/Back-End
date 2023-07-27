package project.linkarchive.backend.relationship.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/follower-list/{userId}")
    public ResponseEntity<List<FollowResponse>> getFollowerList(
            @PathVariable("userId") Long userId,
            AuthInfo authInfo
    ) {
        return ResponseEntity.ok(relationshipQueryService.getFollowerList(userId, authInfo.getId()));
    }

    @GetMapping("/following-list/{userId}")
    public ResponseEntity<List<FollowResponse>> getFollowingList(
            @PathVariable("userId") Long userId,
            AuthInfo authInfo
    ) {
        return ResponseEntity.ok(relationshipQueryService.getFollowingList(userId, authInfo.getId()));
    }
}
