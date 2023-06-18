package project.linkarchive.backend.user.response;

import lombok.Getter;

@Getter
public class UpdateNicknameResponse {

    private String nickname;

    public UpdateNicknameResponse(String nickname) {
        this.nickname = nickname;
    }

}