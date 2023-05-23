package project.linkarchive.backend.user.response;

import lombok.Getter;
import project.linkarchive.backend.user.domain.User;

@Getter
public class ProfileResponse {

    private Long id;
    private String nickname;
    private String introduce;
    private String profileImage;

    public ProfileResponse(User user, String preSignedUrl) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.introduce = user.getIntroduce();
        this.profileImage = preSignedUrl;
    }

}