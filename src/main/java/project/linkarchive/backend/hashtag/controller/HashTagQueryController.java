package project.linkarchive.backend.hashtag.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.hashtag.response.UserTagListResponse;
import project.linkarchive.backend.hashtag.service.HashTagQueryService;
import project.linkarchive.backend.security.AuthInfo;

@RestController
public class HashTagQueryController {

    private final HashTagQueryService hashTagQueryService;

    public HashTagQueryController(HashTagQueryService hashTagQueryService) {
        this.hashTagQueryService = hashTagQueryService;
    }

    @GetMapping("/tags")
    public ResponseEntity<UserTagListResponse> getUserTagList(AuthInfo authInfo) {
        UserTagListResponse userTagList = hashTagQueryService.getLoginUserTagList(authInfo.getId());
        return ResponseEntity.ok(userTagList);
    }

}