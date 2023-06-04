package project.linkarchive.backend.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import project.linkarchive.backend.advice.exception.custom.InvalidException;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.auth.domain.RefreshToken;
import project.linkarchive.backend.auth.repository.RefreshTokenRepository;
import project.linkarchive.backend.auth.response.KakaoProfile;
import project.linkarchive.backend.auth.response.LoginResponse;
import project.linkarchive.backend.auth.response.OauthToken;
import project.linkarchive.backend.profileImage.domain.ProfileImage;
import project.linkarchive.backend.profileImage.repository.ProfileImageRepository;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.UserRepository;
import project.linkarchive.backend.util.JwtUtil;

import javax.transaction.Transactional;

import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.*;

@Service
@Transactional
public class OAuthService {

    private final static int TOKEN_DATA_INDEX = 1;
    private final static String REGEX = " ";

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

    public LoginResponse login(String code, String redirectUrl, String userAgent) {
        OauthToken oauthToken = jwtUtil.getToken(code, redirectUrl);
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

    public LoginResponse reissueToken(String rt) {
        String[] tokenData = rt.split(REGEX);
        String token = tokenData[TOKEN_DATA_INDEX];

        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(token).orElseThrow(() -> new InvalidException(IS_NOT_REFRESH_TOKEN));

        if (jwtUtil.isValidatedToken(token)) {
            User user = userRepository.findById(refreshToken.getUser().getId()).orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
            String newAccessToken = jwtUtil.createAccessToken(user);
            String newRefreshToken = jwtUtil.createRefreshToken(user);

            if (refreshTokenRepository.existsByUserIdAndAgent(refreshToken.getUser().getId(), refreshToken.getAgent())) {
                RefreshToken reissuedToken = RefreshToken.build(newRefreshToken, refreshToken.getAgent(), user);
                refreshToken.updateRefreshToken(reissuedToken);
            }
            return new LoginResponse(user, newAccessToken, newRefreshToken);

        } else {
            throw new InvalidException(INVALID_TOKEN);
        }
    }
}