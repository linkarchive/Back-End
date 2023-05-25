package project.linkarchive.backend.isLinkRead.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.advice.success.SuccessResponse;
import project.linkarchive.backend.isLinkRead.service.IsLinkReadApiService;
import project.linkarchive.backend.security.AuthInfo;

import static org.springframework.http.HttpStatus.CREATED;
import static project.linkarchive.backend.advice.success.SuccessCodeConst.LINK_READ;

@RestController
public class IsLinkReadApiController {

    private final IsLinkReadApiService isLinkReadApiService;

    public IsLinkReadApiController(IsLinkReadApiService isLinkReadApiService) {
        this.isLinkReadApiService = isLinkReadApiService;
    }

    @PostMapping("/read/link/{linkId}")
    public ResponseEntity<SuccessResponse> isLinkRead(
            @PathVariable("linkId") Long linkId,
            AuthInfo authInfo
    ) {
        isLinkReadApiService.isLinkRead(linkId, authInfo.getId());
        return ResponseEntity.status(CREATED).body(new SuccessResponse(LINK_READ));
    }

}