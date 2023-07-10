package project.linkarchive.backend.hashtag.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.hashtag.response.TagListResponse;
import project.linkarchive.backend.hashtag.service.HashTagQueryService;

@RestController
public class HashTagQueryController {

    private final HashTagQueryService hashTagQueryService;

    public HashTagQueryController(HashTagQueryService hashTagQueryService) {
        this.hashTagQueryService = hashTagQueryService;
    }

    @GetMapping("/tags/user/{nickname}")
    public ResponseEntity<TagListResponse> getUserTagList(
            @PathVariable("nickname") String nickname
    ) {
        TagListResponse tagList = hashTagQueryService.getUserTagList(nickname);
        return ResponseEntity.ok(tagList);
    }

    @GetMapping("/limited-tags/user/{nickname}")
    public ResponseEntity<TagListResponse> getTagList(
            @PathVariable("nickname") String nickname,
            @RequestParam("size") int size
    ) {
        TagListResponse tagList = hashTagQueryService.getLimitedTagList(nickname, size);
        return ResponseEntity.ok(tagList);
    }

}