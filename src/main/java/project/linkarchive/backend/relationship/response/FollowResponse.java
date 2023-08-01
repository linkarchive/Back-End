package project.linkarchive.backend.relationship.response;

import lombok.Getter;
import project.linkarchive.backend.user.domain.User;

@Getter
public class FollowResponse {
    private Long userId;
    private String nickname;
    private String introduce;
    private String profileImageFileName;
    private Boolean isFollow;

    public FollowResponse(User user, String url, Boolean isFollow) {

        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.introduce = user.getIntroduce();
        this.profileImageFileName = url;
        this.isFollow = isFollow;
    }

}
