package project.linkarchive.backend.util.setUpData;

import project.linkarchive.backend.profileImage.domain.ProfileImage;
import project.linkarchive.backend.user.domain.User;

import static project.linkarchive.backend.util.constant.Constants.*;

public class ProfileImageSetUpData extends MockDataGenerator {

    protected void setUpProfileImage() {
        profileImage = ProfileImage.builder()
                .id(PROFILE_IMAGE_ID)
                .profileImageFilename(PROFILE_IMAGE_FILENAME)
                .build();
    }

}