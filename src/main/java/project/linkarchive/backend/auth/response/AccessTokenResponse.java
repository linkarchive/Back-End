package project.linkarchive.backend.auth.response;

import lombok.Getter;

@Getter
public class AccessTokenResponse {

    String accessToken;
    String refreshToken;

    public AccessTokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
