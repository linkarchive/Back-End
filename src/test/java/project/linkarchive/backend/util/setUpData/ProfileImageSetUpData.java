package project.linkarchive.backend.util.setUpData;

import project.linkarchive.backend.profileImage.domain.ProfileImage;
import project.linkarchive.backend.user.domain.User;

import static project.linkarchive.backend.util.constant.Constants.*;

public class ProfileImageSetUpData extends SetUpData {

    protected void setUpUser() {
        user = User.builder()
                .id(USER_ID)
                .socialId(SOCIAL_ID)
                .nickname(EMPTY)
                .email(EMAIL)
                .introduce(EMPTY)
                .build();
    }

    protected void setUpProfileImage() {
        profileImage = ProfileImage.builder()
                .id(PROFILE_IMAGE_ID)
                .profileImageFilename(PROFILE_IMAGE_FILENAME)
                .user(user)
                .build();
    }

}