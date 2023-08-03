package project.linkarchive.backend.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import project.linkarchive.backend.auth.domain.RefreshToken;
import project.linkarchive.backend.auth.repository.RefreshTokenRepository;
import project.linkarchive.backend.auth.response.KakaoProfile;
import project.linkarchive.backend.auth.response.LoginResponse;
import project.linkarchive.backend.auth.response.OauthToken;
import project.linkarchive.backend.auth.response.AccessTokenResponse;
import project.linkarchive.backend.profileImage.domain.ProfileImage;
import project.linkarchive.backend.profileImage.repository.ProfileImageRepository;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.UserRepository;
import project.linkarchive.backend.util.JwtUtil;

import javax.transaction.Transactional;

import java.util.Optional;

import static project.linkarchive.backend.advice.data.DataConstants.AUTH_KAKAO;

@Service
@Transactional
public class OAuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final ProfileImageRepository profileImageRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${cloud.aws.s3.default-image}")
    private String DEFAULT_IMAGE;

    public OAuthService(JwtUtil jwtUtil, UserRepository userRepository, ProfileImageRepository profileImageRepository, RefreshTokenRepository refreshTokenRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.profileImageRepository = profileImageRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public LoginResponse loginForBackEnd(String code, String userAgent) {
        OauthToken oauthToken = jwtUtil.getTokenForBackEnd(code);
        KakaoProfile kakaoProfile = jwtUtil.getUserInfo(oauthToken.getAccess_token());

        User findUser = userRepository.findBySocialId(kakaoProfile.getId())
                .orElseGet(() -> {
                    ProfileImage profileImage = ProfileImage.create(DEFAULT_IMAGE);
                    User user = User.create(kakaoProfile, profileImage);
                    userRepository.save(user);

                    return user;
                });

        String newAccessToken = jwtUtil.createAccessToken(findUser);
        String newRefreshToken = jwtUtil.createRefreshToken(findUser);

        RefreshToken token = RefreshToken.build(newRefreshToken, userAgent, findUser);

        Optional<RefreshToken> savedRefreshToken = refreshTokenRepository.findByUserIdAndAgent(findUser.getId(), userAgent);
        savedRefreshToken.ifPresentOrElse
                (refreshToken -> refreshToken.updateRefreshToken(token)
                        , () -> refreshTokenRepository.save(token));

        return new LoginResponse(findUser, newAccessToken, newRefreshToken);
    }

    public LoginResponse login(String code, String referer, String userAgent) {
        String redirectUri = referer + AUTH_KAKAO;
        OauthToken oauthToken = jwtUtil.getToken(code, redirectUri);
        KakaoProfile kakaoProfile = jwtUtil.getUserInfo(oauthToken.getAccess_token());

        User findUser = userRepository.findBySocialId(kakaoProfile.getId())
                .orElseGet(() -> {
                    ProfileImage profileImage = ProfileImage.create(DEFAULT_IMAGE);
                    User user = User.create(kakaoProfile, profileImage);
                    userRepository.save(user);

                    return user;
                });

        String newAccessToken = jwtUtil.createAccessToken(findUser);
        String newRefreshToken = jwtUtil.createRefreshToken(findUser);

        RefreshToken token = RefreshToken.build(newRefreshToken, userAgent, findUser);

        Optional<RefreshToken> savedRefreshToken = refreshTokenRepository.findByUserIdAndAgent(findUser.getId(), userAgent);
        savedRefreshToken.ifPresentOrElse
                (refreshToken -> refreshToken.updateRefreshToken(token)
                        , () -> refreshTokenRepository.save(token));

        return new LoginResponse(findUser, newAccessToken, newRefreshToken);
    }

    public AccessTokenResponse publishToken(String accessToken, String refreshToken) {
        return jwtUtil.publishToken(accessToken, refreshToken);
    }

}