package project.linkarchive.backend.url.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.advice.success.SuccessCodeConst;
import project.linkarchive.backend.advice.success.SuccessResponse;
import project.linkarchive.backend.url.request.UrlCreateRequest;
import project.linkarchive.backend.url.service.UrlApiService;

@RestController
public class UrlApiController {

    private final UrlApiService urlApiService;

    public UrlApiController(UrlApiService urlApiService) {
        this.urlApiService = urlApiService;
    }

    @PostMapping("/url")
    public ResponseEntity<SuccessResponse> create(@RequestBody UrlCreateRequest request) {
        urlApiService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(SuccessCodeConst.URL_CREATE));
    }

}