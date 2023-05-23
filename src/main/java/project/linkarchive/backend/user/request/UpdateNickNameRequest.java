package project.linkarchive.backend.user.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateNickNameRequest {

    private String nickName;

    public UpdateNickNameRequest(String nickName) {
        this.nickName = nickName;
    }

}