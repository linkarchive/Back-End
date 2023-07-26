package project.linkarchive.backend.relationship.response;

import lombok.Builder;
import lombok.Getter;
import project.linkarchive.backend.user.domain.User;

@Getter
public class FollowInfoResponse {
    private Long userId;
    private String nickname;
    private String profileUrl;
    private boolean isFollow;

    @Builder
    public FollowInfoResponse(User user, String url, boolean isFollow) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.profileUrl = url;
        this.isFollow = isFollow;
    }

}
