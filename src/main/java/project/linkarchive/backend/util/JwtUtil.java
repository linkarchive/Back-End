package project.linkarchive.backend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import project.linkarchive.backend.advice.exception.custom.InvalidException;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.advice.exception.custom.UnauthorizedException;
import project.linkarchive.backend.auth.domain.RefreshToken;
import project.linkarchive.backend.auth.repository.RefreshTokenRepository;
import project.linkarchive.backend.auth.response.AccessTokenResponse;
import project.linkarchive.backend.auth.response.KakaoProfile;
import project.linkarchive.backend.auth.response.OauthToken;
import project.linkarchive.backend.auth.response.RefreshTokenResponse;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.UserRepository;

import java.security.Key;
import java.util.Date;

import static org.springframework.http.HttpMethod.POST;
import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.*;

@Slf4j
@Component
public class JwtUtil {

    private static final Long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 2L;
    private static final Long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 30L;
    private final static int TOKEN_DATA_INDEX = 1;
    private final static String BLANK = " ";
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    @Value("${oauth.client.registration.kakao.grant_type}")
    private String GRANT_TYPE;
    @Value("${oauth.client.registration.kakao.client_id}")
    private String CLIENT_ID;
    @Value("${oauth.client.registration.kakao.redirect_uri}")
    private String REDIRECT_URI;
    @Value("${jwt.key}")
    private String SECRET_KEY;

    public JwtUtil(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String createAccessToken(User user) {
        return createJwtToken(user, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public String createRefreshToken(User user) {
        return createJwtToken(user, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    public String createJwtToken(User user, long expirationTime) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expirationMillis = nowMillis + expirationTime;
        Date expiration = new Date(expirationMillis);

        String jwtToken = Jwts.builder()
                .setId(user.getId().toString())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSigningKey())
                .compact();

        return jwtToken;
    }

    public OauthToken getToken(String code, String redirectUri) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", GRANT_TYPE);
        params.add("client_id", CLIENT_ID);
        params.add("redirect_uri", redirectUri);
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
            throw new UnauthorizedException(INVALID_AUTHORIZATION_CODE);
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

    public Long getUserId(String token) {
        Long userId;

        if (isValidatedToken(token)) {
            userId = Long.valueOf(Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getId());
        } else {
            throw new UnauthorizedException(INVALID_TOKEN);
        }

        return userId;
    }

    public boolean isValidatedToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public AccessTokenResponse publishAccessToken(String accessToken, String refreshToken) {
        String accessTokenWithoutBearer = getOnlyToken(accessToken);
        String refreshTokenWithoutBearer = getOnlyToken(refreshToken);

        if (!isValidatedToken(accessTokenWithoutBearer)) { // 엑세스 토큰이 유효하지 않을 떄 엑세스 토큰을 새로 발급할 수 있음
            RefreshToken savedRefreshToken = refreshTokenRepository.findByRefreshToken(refreshTokenWithoutBearer)
                    .orElseThrow(() -> new UnauthorizedException(INVALID_TOKEN));

            if (isValidatedToken(refreshTokenWithoutBearer)) { // + 엑세스 토큰을 새로 발급하기 위해서는 리프레시 토큰은 유효해야함
                User user = userRepository.findById(savedRefreshToken.getUser().getId())
                        .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));

                String newAccessToken = createAccessToken(user);

                return new AccessTokenResponse(newAccessToken);
            } else {
                throw new UnauthorizedException(INVALID_TOKEN);
            }
        }
        throw new InvalidException(ACCESS_TOKEN_STILL_VALID);
    }

    public RefreshTokenResponse publishRefreshToken(String refreshToken) {
        String refreshTokenWithoutBearer = getOnlyToken(refreshToken);

        RefreshToken savedRefreshToken = refreshTokenRepository.findByRefreshToken(refreshTokenWithoutBearer)
                .orElseThrow(() -> new UnauthorizedException(INVALID_TOKEN));

        if (isValidatedToken(refreshTokenWithoutBearer)) {
            User user = userRepository.findById(savedRefreshToken.getUser().getId())
                    .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));

            String newAccessToken = createAccessToken(user);
            String newRefreshToken = createRefreshToken(user);

            if (refreshTokenRepository.existsByUserIdAndAgent(savedRefreshToken.getUser().getId(), savedRefreshToken.getAgent())) {
                RefreshToken refreshedToken = RefreshToken.build(newRefreshToken, savedRefreshToken.getAgent(), user);
                savedRefreshToken.updateRefreshToken(refreshedToken);
            }

            return new RefreshTokenResponse(newAccessToken, newRefreshToken);
        } else {
            throw new UnauthorizedException(INVALID_TOKEN);
        }
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    private String getOnlyToken(String token) {
        String[] tokenData = token.split(BLANK);
        String tokenWithoutBearer = tokenData[TOKEN_DATA_INDEX];

        return tokenWithoutBearer;
    }
}