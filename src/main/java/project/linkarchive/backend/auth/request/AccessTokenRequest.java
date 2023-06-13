package project.linkarchive.backend.auth.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccessTokenRequest {
    private String accessToken;

    public AccessTokenRequest(String accessToken) {
        this.accessToken = accessToken;
    }
}