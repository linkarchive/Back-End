package project.linkarchive.backend.profileImage.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.util.constant.SetUpData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static project.linkarchive.backend.util.constant.Constants.NEW_PROFILE_IMAGE_FILENAME;
import static project.linkarchive.backend.util.constant.Constants.PROFILE_IMAGE_FILENAME;

class ProfileImageTest extends SetUpData {

    @DisplayName("프로필 이미지 getProfileImageFileName - Domain")
    @Test
    void testGetProfileImageFilename() {
        String profileImageFilename = profileImage.getProfileImageFilename();

        assertNotNull(profileImageFilename);
        assertEquals(PROFILE_IMAGE_FILENAME, profileImageFilename);
    }

    @DisplayName("프로필 이미지 getUser - Domain")
    @Test
    void testGetUser() {
        User getUser = profileImage.getUser();

        assertNotNull(getUser);
        assertEquals(user, getUser);
    }

    @DisplayName("프로필 이미지 Builder 패턴 - Domain")
    @Test
    void testBuilder() {
        String profileImageFilename = profileImage.getProfileImageFilename();

        ProfileImage getProfileImage = ProfileImage.builder()
                .profileImageFilename(profileImageFilename)
                .user(user)
                .build();

        assertNotNull(profileImageFilename);
        assertNotNull(getProfileImage);
        assertEquals(profileImageFilename, getProfileImage.getProfileImageFilename());
        assertEquals(user, getProfileImage.getUser());
        assertEquals(profileImage.getUser(), getProfileImage.getUser());
    }

    @DisplayName("프로필 이미지 Build 메서드 - Domain")
    @Test
    void testBuild() {
        String profileImageFilename = profileImage.getProfileImageFilename();

        ProfileImage getProfileImage = ProfileImage.build(profileImageFilename, user);

        assertNotNull(profileImageFilename);
        assertNotNull(getProfileImage);
        assertEquals(profileImageFilename, getProfileImage.getProfileImageFilename());
        assertEquals(user, getProfileImage.getUser());
        assertEquals(profileImage.getUser(), getProfileImage.getUser());
    }

    @DisplayName("프로필 이미지 UpdateProfileImage - Domain")
    @Test
    void testUpdateProfileImage() {
        String newProfileImageFilename = NEW_PROFILE_IMAGE_FILENAME;

        profileImage.updateProfileImage(newProfileImageFilename);

        assertNotNull(newProfileImageFilename);
        assertEquals(profileImage.getProfileImageFilename(), newProfileImageFilename);
    }

}