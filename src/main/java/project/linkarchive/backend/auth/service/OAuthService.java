package project.linkarchive.backend.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import project.linkarchive.backend.advice.exception.ExceptionCodeConst;
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
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.*;

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

    public LoginResponse login(String code, String userAgent) {
        OauthToken oauthToken = jwtUtil.getToken(code);
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

    public LoginResponse checkToken(String refreshToken, Long userId){
        String[] tokenData = refreshToken.split(" ");
        String token = tokenData[1];

        if (jwtUtil.isUnexpiredToken(token)) {
            // 만료시간 보여주면 좋을듯
            throw new InvalidException(ExceptionCodeConst.NOT_ACCEPTABLE_CONTENT_TYPE);
        }
        return reissueToken(refreshToken, userId);
    }

    public LoginResponse reissueToken(String oldRefreshToken, Long userId){
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId).orElseThrow(()-> new NotFoundException(NOT_FOUND_USER));

        if(!refreshToken.getRefreshToken().equals(oldRefreshToken)){
            throw new InvalidException(INVALID_REFRESH_TOKEN);
        }

            User user = userRepository.findById(userId).orElseThrow(()-> new NotFoundException(NOT_FOUND_USER));
            String newAccessToken = jwtUtil.createAccessToken(user);
            String newRefreshToken = jwtUtil.createRefreshToken(user);

        if (refreshTokenRepository.existsByUserIdAndAgent(refreshToken.getUser().getId(), refreshToken.getAgent())) {
            RefreshToken reissuedToken = RefreshToken.build(newRefreshToken, refreshToken.getAgent(), user);
            refreshToken.updateRefreshToken(reissuedToken);
        }

        return new LoginResponse(user, newAccessToken, newRefreshToken);

    }
}