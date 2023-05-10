package project.linkarchive.backend.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.auth.response.LoginResponse;
import project.linkarchive.backend.auth.response.OauthToken;
import project.linkarchive.backend.auth.service.OAuthService;
import project.linkarchive.backend.jwt.JwtProperties;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService oAuthService;

    @PostMapping("/auth/kakao")
    public ResponseEntity<LoginResponse> login(@RequestParam("code") String code) {
        OauthToken oauthToken = oAuthService.getAccessToken(code);
        String accessToken = oAuthService.login(oauthToken.getAccess_token());

        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + accessToken);

        return ResponseEntity.ok(new LoginResponse(accessToken));
    }

}