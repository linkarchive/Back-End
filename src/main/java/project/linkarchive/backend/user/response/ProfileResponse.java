package project.linkarchive.backend.user.response;

import lombok.Getter;
import project.linkarchive.backend.user.domain.User;

@Getter
public class ProfileResponse {

    private Long id;
    private String name;
    private String intro;
    private String profileImage;

    public ProfileResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.intro = user.getIntroduce();
        this.profileImage = user.getProfileImage().getProfileImage();
    }

}


