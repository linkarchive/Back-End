package project.linkarchive.backend.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import project.linkarchive.backend.advice.exception.custom.InvalidException;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.auth.response.KakaoProfile;
import project.linkarchive.backend.auth.response.LoginResponse;
import project.linkarchive.backend.auth.response.OauthToken;
import project.linkarchive.backend.jwt.JwtProperties;
import project.linkarchive.backend.jwt.JwtUtil;
import project.linkarchive.backend.user.domain.ProfileImage;
import project.linkarchive.backend.user.domain.RefreshToken;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.RefreshTokenRepository;
import project.linkarchive.backend.user.repository.UserProfileImageRepository;
import project.linkarchive.backend.user.repository.UserRepository;

import javax.transaction.Transactional;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.springframework.http.HttpMethod.POST;
import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.*;

@Service
@Transactional
public class OAuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final UserProfileImageRepository userProfileImageRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${oauth.client.registration.kakao.grant_type}")
    private String GRANT_TYPE;
    @Value("${oauth.client.registration.kakao.client_id}")
    private String CLIENT_ID;
    @Value("${oauth.client.registration.kakao.redirect_uri}")
    private String REDIRECT_URI;

    public OAuthService(JwtUtil jwtUtil, UserRepository userRepository, UserProfileImageRepository userProfileImageRepository, RefreshTokenRepository refreshTokenRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.userProfileImageRepository = userProfileImageRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public LoginResponse login(String code, String redirectUri) {
        OauthToken oauthToken = getToken(code, redirectUri);
        KakaoProfile kakaoProfile = getUserInfo(oauthToken.getAccess_token());

        User findUser = userRepository.findBySocialId(kakaoProfile.getId())
                .map(user -> {
                    userProfileImageRepository.findByUserId(user.getId())
                            .orElseGet(() -> userProfileImageRepository.save(ProfileImage.build(kakaoProfile, user)));

                    return user;
                })
                .orElseGet(() -> {
                    User user = User.build(kakaoProfile);
                    userProfileImageRepository.save(ProfileImage.build(kakaoProfile, user));

                    return user;
                });

        String accessToken = jwtUtil.createAccessToken(findUser);
        String refreshToken = jwtUtil.createRefreshToken(findUser);

        RefreshToken token = RefreshToken.build(refreshToken, findUser);
        refreshTokenRepository.save(token);

        return new LoginResponse(findUser.getId(), accessToken, refreshToken);
    }

    public OauthToken getToken(String code, String redirectUri) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", GRANT_TYPE);
        params.add("client_id", CLIENT_ID);
        params.add("redirect_uri", REDIRECT_URI);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response;
        try {
            response = rt.exchange(
                    "https://kauth.kakao.com/oauth/token",
                    POST,
                    kakaoTokenRequest,
                    String.class);
        } catch (HttpClientErrorException e) {
            throw new InvalidException(INVALID_AUTHORIZATION_CODE);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        OauthToken oauthToken;
        try {
            oauthToken = objectMapper.readValue(response.getBody(), OauthToken.class);
        } catch (JsonProcessingException e) {
            throw new NotFoundException(NOT_FOUND_USER);
        }

        return oauthToken;
    }

    public KakaoProfile getUserInfo(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);
        ResponseEntity<String> kakaoProfileResponse = rt.exchange("https://kapi.kakao.com/v2/user/me", POST, kakaoProfileRequest, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        KakaoProfile kakaoProfile;
        try {
            kakaoProfile = objectMapper.readValue(kakaoProfileResponse.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            throw new NotFoundException(NOT_FOUND_USER);
        }

        return kakaoProfile;
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(JwtProperties.SECRET.getBytes());
    }

    public boolean validate(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public Long getUserId(String token) {
        Long userId;

        if (validate(token)) {
            userId = Long.valueOf(Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getId());
        } else {
            //FIXME: 토큰 유효기간 수정 후 토큰 만료, 유효하지 않음, 시그니처 다름 등의 예외 처리가 필요합니다.
            throw new InvalidException(INVALID_TOKEN);
        }

        return userId;
    }



    private Date toDate(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

}