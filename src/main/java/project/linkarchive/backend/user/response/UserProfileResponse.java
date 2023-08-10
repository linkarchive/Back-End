package project.linkarchive.backend.user.response;

import lombok.Getter;
import project.linkarchive.backend.user.domain.User;

@Getter
public class UserProfileResponse {

    private Long id;
    private String nickname;
    private String introduce;
    private String profileImage;
    private int followerCount;
    private int followingCount;
    private Boolean isFollow;

    public UserProfileResponse(User user, String preSignedUrl, Boolean isFollow) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.introduce = user.getIntroduce();
        this.profileImage = preSignedUrl;
        this.followerCount = user.getFollowerCount();
        this.followingCount = user.getFollowingCount();
        this.isFollow = isFollow;
    }

}
