package project.linkarchive.backend.user.response;

import lombok.Getter;
import project.linkarchive.backend.user.domain.User;

@Getter
public class MyProfileResponse {

    private Long id;
    private String nickname;
    private String introduce;
    private String profileImage;
    private int followerCount;
    private int followingCount;

    public MyProfileResponse(User user, String preSignedUrl) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.introduce = user.getIntroduce();
        this.profileImage = preSignedUrl;
        this.followerCount = user.getFollowerCount();
        this.followingCount = user.getFollowingCount();
    }

}