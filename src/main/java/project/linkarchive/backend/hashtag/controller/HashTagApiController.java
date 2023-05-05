package project.linkarchive.backend.hashtag.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.advice.success.SuccessResponse;
import project.linkarchive.backend.hashtag.request.TagCreateRequest;
import project.linkarchive.backend.hashtag.service.HashTagApiService;

import static project.linkarchive.backend.advice.success.SuccessCodeConst.USER_TAG_CREATE;

@RestController
public class HashTagApiController {

    private final HashTagApiService hashTagApiService;

    public HashTagApiController(HashTagApiService hashTagApiService) {
        this.hashTagApiService = hashTagApiService;
    }

    @PostMapping("/tag")
    public ResponseEntity<SuccessResponse> create(@RequestBody TagCreateRequest request) {
        hashTagApiService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(USER_TAG_CREATE));
    }

}