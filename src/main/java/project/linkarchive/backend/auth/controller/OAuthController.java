package project.linkarchive.backend.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
//        String referer = request.getHeader("Referer");
//        String redirectUri = referer + "auth/kakao";

        String userAgent = request.getHeader("User-Agent");

        LoginResponse loginResponse = oAuthService.login(code, userAgent);

        return ResponseEntity.ok()
                .header(HEADER_STRING, TOKEN_PREFIX + loginResponse.getAccessToken())
                .body(loginResponse);
    }

    @GetMapping("/publish/access-token")
    public AccessTokenResponse refreshAccessToken(
            @RequestHeader("Authorization") String refreshToken
    ) {
        return oAuthService.publishAccessToken(refreshToken);
    }

    @GetMapping("/publish/refresh-token")
    public RefreshTokenResponse renewToken(
            @RequestHeader("Authorization") String refreshToken
    ) {
        return oAuthService.publishRefreshToken(refreshToken);
    }
}