package project.linkarchive.backend.auth.response;

import lombok.Getter;

@Getter
public class TokenResponse {

    String accessToken;
    String refreshToken;

    public TokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
