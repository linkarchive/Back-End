package project.linkarchive.backend.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.linkarchive.backend.auth.request.AccessTokenRequest;
import project.linkarchive.backend.auth.response.AccessTokenResponse;
import project.linkarchive.backend.auth.response.LoginResponse;
import project.linkarchive.backend.auth.response.RefreshTokenResponse;
import project.linkarchive.backend.auth.service.OAuthService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class OAuthController {

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    private final OAuthService oAuthService;

    public OAuthController(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    @PostMapping("/auth/kakao")
    public ResponseEntity<LoginResponse> login(
            @RequestParam("code") String code,
            HttpServletRequest request
    ) {
        String referer = request.getHeader("Referer");
        String redirectUri = referer + "auth/kakao";

        String userAgent = request.getHeader("User-Agent");

        LoginResponse loginResponse = oAuthService.login(code, redirectUri, userAgent);

        return ResponseEntity.ok().body(loginResponse);
    }

    @PostMapping("/publish/access-token")
    public AccessTokenResponse publishAccessToken(
            @RequestHeader("Authorization") String refreshToken,
            @RequestBody AccessTokenRequest accessTokenRequest
            ) {
        return oAuthService.publishAccessToken(accessTokenRequest.getAccessToken(), refreshToken);
    }

    @PostMapping("/publish/refresh-token")
    public RefreshTokenResponse publishRefreshToken(
            @RequestHeader("Authorization") String refreshToken
    ) {
        return oAuthService.publishRefreshToken(refreshToken);
    }

}