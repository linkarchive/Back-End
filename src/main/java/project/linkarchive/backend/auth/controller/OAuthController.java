package project.linkarchive.backend.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.linkarchive.backend.auth.request.AccessTokenRequest;
import project.linkarchive.backend.auth.response.AccessTokenResponse;
import project.linkarchive.backend.auth.response.LoginResponse;
import project.linkarchive.backend.auth.service.OAuthService;

@RestController
public class OAuthController {

    private final OAuthService oAuthService;

    public OAuthController(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    @PostMapping("/auth/kakao/backend")
    public ResponseEntity<LoginResponse> loginByBackEnd(
            @RequestParam("code") String code,
            @RequestHeader("User-Agent") String userAgent
    ) {
        LoginResponse loginResponse = oAuthService.loginForBackEnd(code, userAgent);

        return ResponseEntity.ok().body(loginResponse);
    }

    @PostMapping("/auth/kakao")
    public ResponseEntity<LoginResponse> login(
            @RequestParam("code") String code,
            @RequestHeader("Referer") String referer,
            @RequestHeader("User-Agent") String userAgent
    ) {
        LoginResponse loginResponse = oAuthService.login(code, referer, userAgent);

        return ResponseEntity.ok().body(loginResponse);
    }

    @PostMapping("/publish/access-token")
    public AccessTokenResponse publishAccessToken(
            @RequestHeader("Authorization") String refreshToken,
            @RequestBody AccessTokenRequest accessTokenRequest
    ) {
        return oAuthService.publishAccessToken(accessTokenRequest.getAccessToken(), refreshToken);
    }

}