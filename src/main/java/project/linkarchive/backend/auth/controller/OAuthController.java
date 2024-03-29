package project.linkarchive.backend.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.linkarchive.backend.auth.request.RefreshTokenRequest;
import project.linkarchive.backend.auth.response.AccessTokenResponse;
import project.linkarchive.backend.auth.response.LoginResponse;
import project.linkarchive.backend.auth.service.LocalLoginService;
import project.linkarchive.backend.auth.service.OauthService;

@RestController
public class OAuthController {

    private final LocalLoginService localLoginService;
    private final OauthService oauthService;

    public OAuthController(LocalLoginService localLoginService, OauthService oauthService) {
        this.localLoginService = localLoginService;
        this.oauthService = oauthService;
    }

    @PostMapping("/auth/local")
    public ResponseEntity<LoginResponse> localLogin(
            @RequestHeader("User-Agent") String userAgent
    ) {
        LoginResponse loginResponse = localLoginService.login(userAgent);

        return ResponseEntity.ok().body(loginResponse);
    }

    @PostMapping("/auth/{registration}")
    public ResponseEntity<LoginResponse> oauthLogin(
            @PathVariable("registration") String registration,
            @RequestParam("code") String code,
            @RequestHeader("Referer") String referer,
            @RequestHeader("User-Agent") String userAgent
    ) {
        LoginResponse loginResponse = oauthService.redirect(registration, referer, code, userAgent);

        return ResponseEntity.ok().body(loginResponse);
    }

    @PostMapping("/publish/access-token")
    public AccessTokenResponse publishAccessToken(
            @RequestBody RefreshTokenRequest request,
            @RequestHeader("User-Agent") String userAgent
    ) {
        return oauthService.publishAccessToken(request.getRefreshToken(), userAgent);
    }

}