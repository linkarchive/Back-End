package project.linkarchive.backend.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import project.linkarchive.backend.advice.exception.BusinessException;
import project.linkarchive.backend.advice.exception.ExceptionCodeConst;
import project.linkarchive.backend.auth.KakaoProfile;
import project.linkarchive.backend.auth.OauthToken;
import project.linkarchive.backend.jwt.JwtProperties;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.domain.UserProfileImage;
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

    public OauthToken getAccessToken(String code) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "007ec5398a74c54203469840f4a3370e");
        params.add("redirect_uri", "http://localhost:3000/auth/kakao");
        params.add("code", code);


        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        OauthToken oauthToken;

        try {
            oauthToken = objectMapper.readValue(response.getBody(), OauthToken.class);
        } catch (
                JsonProcessingException e) {
            throw new BusinessException(ExceptionCodeConst.NOT_FOUND_USER);
        }

        return oauthToken;
    }


    public String saveUserAndGetToken(String token) {
        KakaoProfile profile = findProfile(token);

        User user = userRepository.findByEmail(profile.getKakaoAccount().getEmail());
        if (user == null) {
            UserProfileImage userProfileImage = UserProfileImage.builder()
                    .userProfileImage(profile.getKakaoAccount().getProfile().getProfileImageUrl())
                    .build();

            user = User.builder()
                    .socialId(profile.getId())
                    .userProfileImage(userProfileImage)
                    .userName(profile.getKakaoAccount().getProfile().getNickname())
                    .email(profile.getKakaoAccount().getEmail())
                    .userRole("ROLE_USER").build();


            userProfileImage.setUser(user);

            userRepository.save(user);
            userProfileImageRepository.save(userProfileImage);


        }

        return createToken(user);

    }
    public String createToken(User user) {
        String jwtToken = Jwts.builder()
                .setId(user.getId().toString())
                .setExpiration(toDate(user.getCreatedAt()))
                .signWith(getSigningKey())
                .compact();


        return jwtToken;
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
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }


    public Long getUserId(String token) {

        Long userId = Long.valueOf(Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token).getBody().getId());
        return userId;

    }


    public KakaoProfile findProfile(String token) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers);

        ResponseEntity<String> kakaoProfileResponse = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);



        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper.readValue(kakaoProfileResponse.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ExceptionCodeConst.NOT_FOUND_USER);
        }

        return kakaoProfile;
    }


}
