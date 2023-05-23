package project.linkarchive.backend.auth.response;

import lombok.Getter;

@Getter
public class LoginResponse {

    private Long userId;
    private String accessToken;
    private String refreshToken;
    private Boolean isFirstLogin;

    public LoginResponse(Long userId, String accessToken, String refreshToken, Boolean isFirstLogin) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.isFirstLogin = isFirstLogin;
    }

}