package project.linkarchive.backend.relationship.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.relationship.response.FollowInfoResponse;
import project.linkarchive.backend.relationship.service.RelationshipQueryService;

import java.util.List;

@RestController
public class RelationshipQueryController {
    private final RelationshipQueryService relationshipQueryService;

    public RelationshipQueryController(RelationshipQueryService relationshipQueryService) {
        this.relationshipQueryService = relationshipQueryService;
    }

    @GetMapping("/follower-list/{userId}")
    public ResponseEntity<List<FollowInfoResponse>> getFollowerList(
            @PathVariable("userId") Long userId
    ) {
        return ResponseEntity.ok(relationshipQueryService.getFollowerList(userId));
    }

    @GetMapping("/following-list/{userId}")
    public ResponseEntity<List<FollowInfoResponse>> getFollowingList(
            @PathVariable("userId") Long userId
    ) {
        return ResponseEntity.ok(relationshipQueryService.getFollowingList(userId));
    }
}

