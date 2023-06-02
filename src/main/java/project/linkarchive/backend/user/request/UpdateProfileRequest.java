package project.linkarchive.backend.user.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateProfileRequest {

    private String nickname;
    private String introduce;

    public UpdateProfileRequest(String nickname, String introduce) {
        this.nickname = nickname;
        this.introduce = introduce;
    }

}