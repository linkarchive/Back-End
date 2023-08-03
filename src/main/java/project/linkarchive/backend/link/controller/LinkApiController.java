package project.linkarchive.backend.link.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.linkarchive.backend.advice.success.SuccessResponse;
import project.linkarchive.backend.link.request.CreateLinkRequest;
import project.linkarchive.backend.link.service.LinkApiService;
import project.linkarchive.backend.security.AuthInfo;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static project.linkarchive.backend.advice.success.SuccessCodeConst.LINK_CREATE;
import static project.linkarchive.backend.advice.success.SuccessCodeConst.LINK_DELETE;

@RestController
public class LinkApiController {

    private final LinkApiService linkApiService;

    public LinkApiController(LinkApiService linkApiService) {
        this.linkApiService = linkApiService;
    }

    @PostMapping("/link")
    public ResponseEntity<SuccessResponse> create(
            @RequestBody CreateLinkRequest request,
            AuthInfo authInfo
    ) {
        linkApiService.create(request, authInfo.getId());
        return ResponseEntity.status(CREATED).body(new SuccessResponse(LINK_CREATE));
    }

    @PatchMapping("/link/{linkId}")
    public ResponseEntity<SuccessResponse> delete(
            @PathVariable("linkId") Long linkId,
            AuthInfo authInfo
    ) {
        linkApiService.delete(linkId, authInfo.getId());
        return ResponseEntity.status(OK).body(new SuccessResponse(LINK_DELETE));
    }

}