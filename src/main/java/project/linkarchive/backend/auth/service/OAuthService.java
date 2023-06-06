package project.linkarchive.backend.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import project.linkarchive.backend.auth.domain.RefreshToken;
import project.linkarchive.backend.auth.repository.RefreshTokenRepository;
import project.linkarchive.backend.auth.response.*;
import project.linkarchive.backend.profileImage.domain.ProfileImage;
import project.linkarchive.backend.profileImage.repository.ProfileImageRepository;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.UserRepository;
import project.linkarchive.backend.util.JwtUtil;

import javax.transaction.Transactional;

@Service
@Transactional
public class OAuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final ProfileImageRepository userProfileImageRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${cloud.aws.s3.default-image}")
    private String DEFAULT_IMAGE;

    public OAuthService(JwtUtil jwtUtil, UserRepository userRepository, ProfileImageRepository userProfileImageRepository, RefreshTokenRepository refreshTokenRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.userProfileImageRepository = userProfileImageRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public LoginResponse login(String code, String redirectUri, String userAgent) {
        OauthToken oauthToken = jwtUtil.getToken(code, redirectUri);
        KakaoProfile kakaoProfile = jwtUtil.getUserInfo(oauthToken.getAccess_token());

        User findUser = userRepository.findBySocialId(kakaoProfile.getId())
                .map(user -> {
                    userProfileImageRepository.findByUserId(user.getId())
                            .orElseGet(() -> userProfileImageRepository.save(ProfileImage.build(DEFAULT_IMAGE, user)));

                    return user;
                })
                .orElseGet(() -> {
                    User user = User.build(kakaoProfile);
                    userProfileImageRepository.save(ProfileImage.build(DEFAULT_IMAGE, user));

                    return user;
                });

        String accessToken = jwtUtil.createAccessToken(findUser);
        String refreshToken = jwtUtil.createRefreshToken(findUser);

        if (!refreshTokenRepository.existsByUserIdAndAgent(findUser.getId(), userAgent)) {
            RefreshToken token = RefreshToken.build(refreshToken, userAgent, findUser);
            refreshTokenRepository.save(token);
        }

        return new LoginResponse(findUser, accessToken, refreshToken);
    }

    public AccessTokenResponse publishAccessToken(String refreshToken) {

        return jwtUtil.publishAccessToken(refreshToken);

    }

    public RefreshTokenResponse publishRefreshToken(String refreshToken) {

        return jwtUtil.publishRefreshToken(refreshToken);
    }

}