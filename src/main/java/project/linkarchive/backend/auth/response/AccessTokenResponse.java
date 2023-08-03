package project.linkarchive.backend.auth.response;

import lombok.Getter;

@Getter
public class AccessTokenResponse {

    String accessToken;

    public AccessTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
