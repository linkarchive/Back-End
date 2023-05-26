package project.linkarchive.backend.user.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NickNameRequest {

    private String nickname;

    public NickNameRequest(String nickname) {
        this.nickname = nickname;
    }

}