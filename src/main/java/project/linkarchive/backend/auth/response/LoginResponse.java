package project.linkarchive.backend.auth.response;

import lombok.Getter;
import project.linkarchive.backend.user.domain.User;

@Getter
public class LoginResponse {

    private Long userId;
    private String nickname;
    private String accessToken;
    private String refreshToken;

    public LoginResponse(User user, String accessToken, String refreshToken) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}