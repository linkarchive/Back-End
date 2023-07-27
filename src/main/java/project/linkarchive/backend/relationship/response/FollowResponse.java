package project.linkarchive.backend.relationship.response;

import lombok.Builder;
import lombok.Getter;
import project.linkarchive.backend.user.domain.User;

@Getter
public class FollowResponse {
    private Long userId;
    private String nickname;
    private String introduce;
    private String profileImageFileName;
    private boolean isFollow;

    @Builder
    public FollowResponse(User user, String url, boolean isFollow) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.introduce = user.getIntroduce();
        this.profileImageFileName = url;
        this.isFollow = isFollow;
    }

}
