package project.linkarchive.backend.user.response;

import lombok.Getter;
import project.linkarchive.backend.user.domain.User;

@Getter
public class ProfileResponse {

    private Long id;
    private String nickname;
    private String introduce;
    private String profileImageFileName;
    private int followerCount;
    private int followingCount;
    private boolean isFollow;

    public ProfileResponse(User user, String preSignedUrl, boolean isFollow) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.introduce = user.getIntroduce();
        this.profileImageFileName = preSignedUrl;
        this.followerCount = user.getFollowerCount();
        this.followingCount = user.getFollowingCount();
        this.isFollow = isFollow;
    }

}