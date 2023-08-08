package project.linkarchive.backend.hashtag.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.hashtag.response.TagListResponse;
import project.linkarchive.backend.hashtag.service.HashTagQueryService;

@RestController
public class HashTagQueryController {

    private final HashTagQueryService hashTagQueryService;

    public HashTagQueryController(HashTagQueryService hashTagQueryService) {
        this.hashTagQueryService = hashTagQueryService;
    }

    @GetMapping("/tags/user/{userId}")
    public ResponseEntity<TagListResponse> getUserTagList(
            @PathVariable("userId") Long userId
    ) {
        TagListResponse tagList = hashTagQueryService.getUserTagList(userId);
        return ResponseEntity.ok(tagList);
    }

    @GetMapping("/tags/10/user/{userId}")
    public ResponseEntity<TagListResponse> getUserTagList10(
            @PathVariable("userId") Long userId
    ) {
        TagListResponse tagList = hashTagQueryService.getUserTagList10(userId);
        return ResponseEntity.ok(tagList);
    }

    @GetMapping("/tags/archive")
    public ResponseEntity<TagListResponse> getArchiveTagList() {
        TagListResponse tagList = hashTagQueryService.getArchiveTagList();
        return ResponseEntity.ok(tagList);
    }

}