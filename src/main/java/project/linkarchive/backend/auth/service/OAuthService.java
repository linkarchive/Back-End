package project.linkarchive.backend.auth.service;

import org.springframework.stereotype.Service;
import project.linkarchive.backend.auth.response.AccessTokenResponse;
import project.linkarchive.backend.auth.response.LoginResponse;
import project.linkarchive.backend.util.JwtUtil;

import javax.transaction.Transactional;

import static project.linkarchive.backend.auth.AuthProvider.KAKAO;

@Service
@Transactional
public class OauthService {

    private final JwtUtil jwtUtil;
    private final KakaoLoginService kakaoLoginService;

    public OauthService(JwtUtil jwtUtil, KakaoLoginService kakaoLoginService) {
        this.jwtUtil = jwtUtil;
        this.kakaoLoginService = kakaoLoginService;
    }

    public LoginResponse redirect(String registration, String referer, String code, String userAgent) {
        if (KAKAO.getAuthProvider().equals(registration)) {
            return kakaoLoginService.login(code, referer, userAgent);
        }

        return null;
    }

    public AccessTokenResponse publishAccessToken(String accessToken, String refreshToken) {
        return jwtUtil.publishAccessToken(accessToken, refreshToken);
    }

}