package project.linkarchive.backend.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import project.linkarchive.backend.auth.domain.RefreshToken;
import project.linkarchive.backend.auth.repository.RefreshTokenRepository;
import project.linkarchive.backend.auth.response.KakaoProfile;
import project.linkarchive.backend.auth.response.LoginResponse;
import project.linkarchive.backend.auth.response.OauthToken;
import project.linkarchive.backend.profileImage.domain.ProfileImage;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.UserRepository;
import project.linkarchive.backend.util.JwtUtil;

import javax.transaction.Transactional;
import java.util.Optional;

import static project.linkarchive.backend.advice.data.DataConstants.SOCIAL_LOGIN;
import static project.linkarchive.backend.auth.AuthProvider.LOCAL;

@Service
@Transactional
public class LocalLoginService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${cloud.aws.s3.default-image}")
    private String DEFAULT_IMAGE;
    @Value("${cloud.aws.s3.url}")
    private String URL;

    public LocalLoginService(JwtUtil jwtUtil, UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public LoginResponse login(String userAgent) {
        User findUser = userRepository.findBySocialId(SOCIAL_LOGIN)
                .orElseGet(() -> {
                    ProfileImage profileImage = ProfileImage.create(DEFAULT_IMAGE);
                    User user = User.localCreate(profileImage);
                    userRepository.save(user);

                    return user;
                });

        String newAccessToken = jwtUtil.createAccessToken(findUser);
        String newRefreshToken = jwtUtil.createRefreshToken(findUser);

        RefreshToken token = RefreshToken.create(newRefreshToken, userAgent, findUser);

        Optional<RefreshToken> savedRefreshToken = refreshTokenRepository.findByUserIdAndAgent(findUser.getId(), userAgent);
        savedRefreshToken.ifPresentOrElse(
                refreshToken -> refreshToken.updateRefreshToken(token),
                () -> refreshTokenRepository.save(token)
        );

        return new LoginResponse(findUser, URL + findUser.getProfileImage().getProfileImageFilename(), newAccessToken, newRefreshToken);
    }

}