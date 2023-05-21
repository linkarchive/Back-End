package project.linkarchive.backend.auth.response;

import lombok.Getter;

@Getter
public class LoginResponse {

    private Long userId;
    private String accessToken;

    public LoginResponse(Long userId, String accessToken) {
        this.userId = userId;
        this.accessToken = accessToken;
    }

}