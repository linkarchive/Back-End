package project.linkarchive.backend.user.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.linkarchive.backend.user.domain.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileResponse {

    private Long id;
    private String nickname;
    private String introduce;
    private String profileImageFileName;

    public ProfileResponse(User user, String preSignedUrl) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.introduce = user.getIntroduce();
        this.profileImageFileName = preSignedUrl;
    }

}