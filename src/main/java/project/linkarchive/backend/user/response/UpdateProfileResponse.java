package project.linkarchive.backend.user.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.linkarchive.backend.user.domain.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateProfileResponse {

    private String nickname;
    private String introduce;

    public UpdateProfileResponse(User user) {
        this.nickname = user.getNickname();
        this.introduce = user.getIntroduce();
    }

}