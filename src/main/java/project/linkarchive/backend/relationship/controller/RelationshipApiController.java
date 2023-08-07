package project.linkarchive.backend.relationship.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.advice.success.SuccessResponse;
import project.linkarchive.backend.relationship.service.RelationshipApiService;
import project.linkarchive.backend.security.AuthInfo;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static project.linkarchive.backend.advice.success.SuccessCodeConst.FOLLOW_USER;
import static project.linkarchive.backend.advice.success.SuccessCodeConst.UNFOLLOW_USER;

@RestController
public class RelationshipApiController {

    private final RelationshipApiService relationshipApiService;

    public RelationshipApiController(RelationshipApiService relationshipApiService) {
        this.relationshipApiService = relationshipApiService;
    }

    @PostMapping("/follow/user/{followeeId}")
    public ResponseEntity<SuccessResponse> followUser(
            @PathVariable(value = "followeeId") Long followeeId,
            AuthInfo authInfo
    ) {
        relationshipApiService.followUser(followeeId, authInfo.getId());
        return ResponseEntity.status(CREATED).body(new SuccessResponse(FOLLOW_USER));
    }

    @DeleteMapping("/unfollow/user/{followeeId}")
    public ResponseEntity<SuccessResponse> unfollowUser(
            @PathVariable(value = "followeeId") Long followeeId,
            AuthInfo authInfo
    ) {
        relationshipApiService.unfollowUser(followeeId, authInfo.getId());
        return ResponseEntity.status(OK).body(new SuccessResponse(UNFOLLOW_USER));
    }

}