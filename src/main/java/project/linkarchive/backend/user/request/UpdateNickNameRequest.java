package project.linkarchive.backend.user.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateNickNameRequest {

    private String nickname;

    public UpdateNickNameRequest(String nickname) {
        this.nickname = nickname;
    }

}