package project.linkarchive.backend.hashtag.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.hashtag.response.TagListResponse;
import project.linkarchive.backend.hashtag.response.UserTagListResponse;
import project.linkarchive.backend.hashtag.service.HashTagQueryService;
import project.linkarchive.backend.security.AuthInfo;

@RestController
@PreAuthorize("isAuthenticated()")
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

    // 사용자 별 자주 사용하는 해시태그 N개 조회 007
    @GetMapping("/tags/user/{userId}")
    public ResponseEntity<TagListResponse> getTagList(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "size") Long size
    ) {
        TagListResponse tagList = hashTagQueryService.getTagList(userId, size);
        return ResponseEntity.ok(tagList);
    }


}