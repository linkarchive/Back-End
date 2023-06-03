package project.linkarchive.backend.util.setUpData;

import org.junit.jupiter.api.BeforeEach;
import project.linkarchive.backend.profileImage.domain.ProfileImage;
import project.linkarchive.backend.user.domain.User;

import static project.linkarchive.backend.util.constant.Constants.*;

public class ProfileImageSetUpData {

    public User user;
    public ProfileImage profileImage;

    @BeforeEach
    public void setup() {

        user = User.builder()
                .socialId(SOCIAL_ID)
                .nickname(NICKNAME)
                .email(EMAIL)
                .introduce(INTRODUCE)
                .build();

        profileImage = ProfileImage.builder()
                .profileImageFilename(PROFILE_IMAGE_FILENAME)
                .user(user)
                .build();

    }

}