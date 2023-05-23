package project.linkarchive.backend.link.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.advice.success.SuccessCodeConst;
import project.linkarchive.backend.advice.success.SuccessResponse;
import project.linkarchive.backend.link.request.CreateLinkRequest;
import project.linkarchive.backend.link.service.LinkApiService;
import project.linkarchive.backend.security.AuthInfo;

@RestController
public class LinkApiController {

    private final LinkApiService linkApiService;

    public LinkApiController(LinkApiService linkApiService) {
        this.linkApiService = linkApiService;
    }

    @PostMapping("/link")
    public ResponseEntity<SuccessResponse> create(@RequestBody CreateLinkRequest request, AuthInfo authInfo) {
        linkApiService.create(request, authInfo.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(SuccessCodeConst.LINK_CREATE));
    }

}