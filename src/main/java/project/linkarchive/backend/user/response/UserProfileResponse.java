package project.linkarchive.backend.user.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import project.linkarchive.backend.user.domain.User;

@Getter
@RequiredArgsConstructor
public class UserProfileResponse {
    private Long id;
    private String name;
    private String introduce;
    private String profileImage;

    public UserProfileResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.introduce = user.getIntroduce();
        this.profileImage = user.getProfileImage().getProfileImage();
    }
}


