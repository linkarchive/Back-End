package project.linkarchive.backend.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.auth.response.LoginResponse;
import project.linkarchive.backend.auth.service.OAuthService;
import project.linkarchive.backend.jwt.JwtProperties;

import javax.servlet.http.HttpServletRequest;

@RestController
public class OAuthController {

    private final OAuthService oAuthService;

    public OAuthController(OAuthService oAuthService) {
        this.oAuthService = oAuthService;
    }

    @PostMapping("/auth/kakao")
    public ResponseEntity<LoginResponse> login(@RequestParam("code") String code, HttpServletRequest request) {
        // Referer는 HTTP 헤더 중 하나로, 현재 요청을 보내는 페이지의 주소 URL이에요. *참고*
        String referer = request.getHeader("Referer");
        String redirectUri = referer + "auth/kakao";
        String accessToken = oAuthService.login(code, redirectUri);

        return ResponseEntity.ok()
                .header(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + accessToken)
                .body(new LoginResponse(accessToken));
    }

}