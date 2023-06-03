package project.linkarchive.backend.profileImage.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.util.setUpData.ProfileImageSetUpData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static project.linkarchive.backend.util.constant.Constants.NEW_PROFILE_IMAGE_FILENAME;
import static project.linkarchive.backend.util.constant.Constants.PROFILE_IMAGE_FILENAME;

class ProfileImageTest extends ProfileImageSetUpData {

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
        ProfileImage getProfileImage = ProfileImage.builder()
                .profileImageFilename(PROFILE_IMAGE_FILENAME)
                .user(user)
                .build();

        assertNotNull(getProfileImage);
        assertEquals(PROFILE_IMAGE_FILENAME, getProfileImage.getProfileImageFilename());
        assertEquals(user, getProfileImage.getUser());
    }

    @DisplayName("프로필 이미지 Build 메서드 - Domain")
    @Test
    void testBuild() {
        ProfileImage getProfileImage = ProfileImage.build(PROFILE_IMAGE_FILENAME, user);

        assertNotNull(getProfileImage);
        assertEquals(PROFILE_IMAGE_FILENAME, getProfileImage.getProfileImageFilename());
        assertEquals(user, getProfileImage.getUser());
    }

    @DisplayName("프로필 이미지 UpdateProfileImage - Domain")
    @Test
    void testUpdateProfileImage() {
        profileImage.updateProfileImage(NEW_PROFILE_IMAGE_FILENAME);

        String updatedProfileImageFileName = profileImage.getProfileImageFilename();

        assertNotNull(updatedProfileImageFileName);
        assertEquals(NEW_PROFILE_IMAGE_FILENAME, updatedProfileImageFileName);
    }

}