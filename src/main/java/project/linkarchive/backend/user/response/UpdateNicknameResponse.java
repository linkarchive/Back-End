package project.linkarchive.backend.user.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateNicknameResponse {

    private String nickname;

    public UpdateNicknameResponse(String nickname) {
        this.nickname = nickname;
    }

}