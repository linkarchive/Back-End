package project.linkarchive.backend.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.RefreshTokenRepository;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final Long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 2L;
    private static final Long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 30L;

    private final RefreshTokenRepository refreshTokenRepository;

    public JwtUtil(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String createAccessToken(User user) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expirationMillis = nowMillis + ACCESS_TOKEN_EXPIRATION_TIME;
        Date expiration = new Date(expirationMillis);

        String jwtAccessToken = Jwts.builder()
                .setId(user.getId().toString())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSigningKey())
                .compact();

        return jwtAccessToken;
    }

    public String createRefreshToken(User user) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expirationMillis = nowMillis + REFRESH_TOKEN_EXPIRATION_TIME;
        Date expiration = new Date(expirationMillis);

        String jwtRefreshToken = Jwts.builder()
                .setId(user.getId().toString())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSigningKey())
                .compact();

        return jwtRefreshToken;
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(JwtProperties.SECRET.getBytes());
    }

}