package project.linkarchive.backend.auth.response;

import lombok.Getter;

@Getter
public class TokenResponse {

    String accessToken;

    public TokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
