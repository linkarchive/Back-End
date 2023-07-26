package project.linkarchive.backend.relationship.response;

import lombok.Builder;
import lombok.Getter;
import project.linkarchive.backend.user.domain.User;

@Getter
public class FollowerResponse {
    private Long userId;
    private String nickname;
    private String profileUrl;

    @Builder
    public FollowerResponse(User user, String url){
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.profileUrl = url;
    }

    public static FollowerResponse create(User user) {
        return FollowerResponse.builder()
                .user(user)
                .build();
    }

}
