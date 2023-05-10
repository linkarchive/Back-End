package project.linkarchive.backend.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import project.linkarchive.backend.advice.exception.BusinessException;
import project.linkarchive.backend.advice.exception.ExceptionCodeConst;
import project.linkarchive.backend.auth.response.KakaoProfile;
import project.linkarchive.backend.auth.response.OauthToken;
import project.linkarchive.backend.jwt.JwtProperties;
import project.linkarchive.backend.user.domain.RefreshToken;
import project.linkarchive.backend.user.domain.ProfileImage;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.RefreshTokenRepository;
import project.linkarchive.backend.user.repository.UserProfileImageRepository;
import project.linkarchive.backend.user.repository.UserRepository;

import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final UserRepository userRepository;
    private final UserProfileImageRepository userProfileImageRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    @Value("${oauth.client.registration.kakao.grant_type}")
    private String GRANT_TYPE;
    @Value("${oauth.client.registration.kakao.client_id}")
    private String CLIENT_ID;
    @Value("${oauth.client.registration.kakao.redirect_uri}")
    private String REDIRECT_URI;

    public OauthToken getAccessToken(String code) {
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
                    HttpMethod.POST,
                    kakaoTokenRequest,
                    String.class);
        } catch (HttpClientErrorException e) {
            throw new BusinessException(ExceptionCodeConst.BAD_REQUEST);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        OauthToken oauthToken;
        try {
            oauthToken = objectMapper.readValue(response.getBody(), OauthToken.class);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ExceptionCodeConst.NOT_FOUND_USER);
        }

        return oauthToken;
    }

    public String login(String token) {
        User user = saveUser(token);
        return createToken(user);
    }

    private User saveUser(String token) {
        KakaoProfile profile = findProfile(token);

        User user = userRepository.findByEmail(profile.getKakaoAccount().getEmail())
                .orElse(User.builder()
                        .socialId(profile.id)
                        .name(profile.getKakaoAccount().getProfile().getNickname())
                        .email(profile.getKakaoAccount().getEmail())
                        .build()
                );

        userProfileImageRepository.findByUserId(user.getId())
                .orElseGet((() -> userProfileImageRepository.save(ProfileImage.builder()
                        .profileImage(profile.getKakaoAccount().getProfile().getProfileImageUrl())
                        .user(user)
                        .build())
                ));

        return user;
    }

    private String createToken(User user) {

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        long expirationMillis = nowMillis + (5 * 60 * 1000);
        Date expiration = new Date(expirationMillis);

        //FIXME: accessToken,refreshToken 유효기간 재설정 필요합니다.
        String jwtAccessToken = Jwts.builder()
                .setId(user.getId().toString())
                .setExpiration(expiration)
                .signWith(getSigningKey())
                .compact();

        String jwtRefreshToken = Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(expiration)
                .setId(user.getId().toString())
                .signWith(getSigningKey())
                .compact();

        RefreshToken refreshToken = RefreshToken.builder()
                .refreshToken(jwtRefreshToken)
                .user(user)
                .build();

        if (refreshTokenRepository.findByUserId(user.getId()) != null) {
            RefreshToken oldRefreshToken = refreshTokenRepository.findByUserId(user.getId());
            oldRefreshToken.setRefreshToken(refreshToken.getRefreshToken());
            refreshTokenRepository.save(oldRefreshToken);
        } else {
            refreshTokenRepository.save(refreshToken);
        }
        return jwtAccessToken;
    }

    private Date toDate(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
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
            throw new BusinessException(ExceptionCodeConst.INVALID_TOKEN);
        }
        return userId;
    }

    public KakaoProfile findProfile(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);
        ResponseEntity<String> kakaoProfileResponse = rt.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, kakaoProfileRequest, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        KakaoProfile kakaoProfile;
        try {
            kakaoProfile = objectMapper.readValue(kakaoProfileResponse.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ExceptionCodeConst.NOT_FOUND_USER);
        }

        return kakaoProfile;
    }

}