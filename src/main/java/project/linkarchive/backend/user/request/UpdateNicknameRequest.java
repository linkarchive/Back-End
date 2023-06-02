package project.linkarchive.backend.user.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateNicknameRequest implements NicknameRequest {

    private String nickname;

    public UpdateNicknameRequest(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String getNickname() {
        return null;
    }

}