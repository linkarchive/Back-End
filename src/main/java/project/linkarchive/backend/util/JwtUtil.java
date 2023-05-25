package project.linkarchive.backend.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import project.linkarchive.backend.user.domain.User;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final Long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 2L;
    private static final Long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 30L;
    @Value("${jwt.key}")
    private String SECRET_KEY;

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

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

}