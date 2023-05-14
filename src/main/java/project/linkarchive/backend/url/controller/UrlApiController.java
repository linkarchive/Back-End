package project.linkarchive.backend.url.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.advice.success.SuccessCodeConst;
import project.linkarchive.backend.advice.success.SuccessResponse;
import project.linkarchive.backend.security.AuthInfo;
import project.linkarchive.backend.url.request.UrlCreateRequest;
import project.linkarchive.backend.url.service.UrlApiService;

@RestController
@PreAuthorize("isAuthenticated()")
public class UrlApiController {

    private final UrlApiService urlApiService;

    public UrlApiController(UrlApiService urlApiService) {
        this.urlApiService = urlApiService;
    }

    @PostMapping("/link")
    public ResponseEntity<SuccessResponse> create(@RequestBody UrlCreateRequest request, AuthInfo authInfo) {
        urlApiService.create(request, authInfo.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(SuccessCodeConst.URL_CREATE));
    }

}