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
import project.linkarchive.backend.user.domain.LinkarchiveToken;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService oAuthService;

    @PostMapping("/auth/kakao")
    public ResponseEntity<LoginResponse> getLogin(@RequestParam("code") String code) {
        OauthToken oauthToken = oAuthService.getAccessToken(code);
        LinkarchiveToken linkarchiveToken = oAuthService.login(oauthToken.getAccess_token());

        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + linkarchiveToken );

        return ResponseEntity.ok(new LoginResponse(linkarchiveToken.getAccessToken(), linkarchiveToken.getRefreshToken()));

    }

    @PostMapping("/token")
    public Long getUserId(@RequestParam("token") String token){

        Long userId = oAuthService.getUserId(token);

        return userId;
    }

}