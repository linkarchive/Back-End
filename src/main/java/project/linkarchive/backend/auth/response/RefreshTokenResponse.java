package project.linkarchive.backend.auth.response;

import lombok.Getter;

@Getter
public class RefreshTokenResponse {

    String accessToken;
    String refreshToken;

    public RefreshTokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
