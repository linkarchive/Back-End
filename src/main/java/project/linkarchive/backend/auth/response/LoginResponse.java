package project.linkarchive.backend.auth.response;

import lombok.Getter;

@Getter
public class LoginResponse {

    private String accessToken;

    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }

}