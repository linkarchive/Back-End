package project.linkarchive.backend.user.response;

import lombok.Getter;
import project.linkarchive.backend.user.domain.User;

@Getter
public class UpdateProfileResponse {

    private String nickname;
    private String introduce;

    public UpdateProfileResponse(User user) {
        this.nickname = user.getNickname();
        this.introduce = user.getIntroduce();
    }

}