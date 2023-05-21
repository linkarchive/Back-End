package project.linkarchive.backend.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import project.linkarchive.backend.user.domain.RefreshToken;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.RefreshTokenRepository;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtUtil {

    private final RefreshTokenRepository refreshTokenRepository;

    public JwtUtil(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String createAccessToken(User user) {
        long nowMillis = System.currentTimeMillis();
        long expirationMillis = nowMillis + (5 * 24 * 60 * 60 * 1000);
        Date expiration = new Date(expirationMillis);

        //FIXME: accessToken,refreshToken 유효기간 재설정 필요합니다.
        String jwtAccessToken = Jwts.builder()
                .setId(user.getId().toString())
                .setExpiration(expiration)
                .signWith(getSigningKey())
                .compact();

        return jwtAccessToken;
    }

    public String createRefreshToken(User user) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        long expirationMillis = nowMillis + (5 * 24 * 60 * 60 * 1000);
        Date expiration = new Date(expirationMillis);

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

        Optional<RefreshToken> oldRefreshToken = refreshTokenRepository.findByUserId(user.getId());
        if (oldRefreshToken.isPresent()) {
            oldRefreshToken.get().renewalToken(jwtRefreshToken);
        } else {
            refreshTokenRepository.save(refreshToken);
        }

        return jwtRefreshToken;
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(JwtProperties.SECRET.getBytes());
    }

}