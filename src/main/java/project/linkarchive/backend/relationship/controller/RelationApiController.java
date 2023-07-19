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
public class RelationApiController {

    private final RelationshipApiService relationshipApiService;

    public RelationApiController(RelationshipApiService relationshipApiService) {
        this.relationshipApiService = relationshipApiService;
    }

    @PostMapping("/follow/{nickname}")
    public ResponseEntity<SuccessResponse> followUser(
            @PathVariable(value = "nickname") String nickname,
            AuthInfo authInfo
    ) {
        relationshipApiService.followUser(nickname, authInfo.getId());
        return ResponseEntity.status(CREATED).body(new SuccessResponse(FOLLOW_USER));
    }

    @DeleteMapping("/unfollow/{nickname}")
    public ResponseEntity<SuccessResponse> unfollowUser(
            @PathVariable(value = "nickname") String nickname,
            AuthInfo authInfo
    ) {
        relationshipApiService.unfollowUser(nickname, authInfo.getId());
        return ResponseEntity.status(OK).body(new SuccessResponse(UNFOLLOW_USER));
    }

}
