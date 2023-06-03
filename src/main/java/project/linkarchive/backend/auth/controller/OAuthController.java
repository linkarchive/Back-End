package project.linkarchive.backend.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.linkarchive.backend.advice.exception.ExceptionCodeConst;
import project.linkarchive.backend.advice.exception.custom.InvalidException;
import project.linkarchive.backend.auth.response.LoginResponse;
import project.linkarchive.backend.auth.service.OAuthService;
import project.linkarchive.backend.security.AuthInfo;
import project.linkarchive.backend.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
public class OAuthController {

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    private final OAuthService oAuthService;
    private final JwtUtil jwtUtil;

    public OAuthController(OAuthService oAuthService, JwtUtil jwtUtil) {
        this.oAuthService = oAuthService;
        this.jwtUtil = jwtUtil;
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

    @GetMapping("/token-reissue")
    public LoginResponse reissueToken(
            @RequestHeader("Authorization") String refreshToken,
            AuthInfo authInfo
    ) {
        return oAuthService.checkToken(refreshToken, authInfo.getId());
//        String[] tokenData = refreshToken.split(" ");
//        String token = tokenData[1];
//
//        if (jwtUtil.isUnexpiredToken(token)) {
//            // 만료시간 보여주면 좋을듯
//            throw new InvalidException(ExceptionCodeConst.NOT_ACCEPTABLE_CONTENT_TYPE);
//        }
//        return oAuthService.reissueToken(refreshToken, authInfo.getId());
    }
}

//    @PostMapping("/logout/kakao")
//    public ResponseEntity<LogoutResponse> logout(HttpServletRequest request) {
//        String authorization = request.getHeader("Authorization");
//
//        return ResponseEntity.ok().body()
//    }