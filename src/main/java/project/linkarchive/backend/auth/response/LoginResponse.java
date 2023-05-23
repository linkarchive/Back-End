package project.linkarchive.backend.auth.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponse {

    private Long userId;
    private String accessToken;
    private String refreshToken;

    public LoginResponse(Long userId) {
        this.userId = userId;
    }

    public LoginResponse(Long userId, String accessToken, String refreshToken) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}