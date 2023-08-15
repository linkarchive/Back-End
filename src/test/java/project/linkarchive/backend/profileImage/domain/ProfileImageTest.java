package project.linkarchive.backend.profileImage.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static project.linkarchive.backend.util.constant.Constants.*;

class ProfileImageTest extends SetUpMockData {

    @BeforeEach
    public void setup() {
        setUpProfileImage();
    }

    @DisplayName("프로필 이미지 getProfileImageId - Domain")
    @Test
    void testGetProfileImageId() {
        assertEquals(PROFILE_IMAGE_ID, profileImage.getId());
    }

    @DisplayName("프로필 이미지 getProfileImageFileName - Domain")
    @Test
    void testGetProfileImageFilename() {
        assertEquals(PROFILE_IMAGE_FILENAME, profileImage.getFileName());
    }

    @DisplayName("프로필 이미지 생성자 - Domain")
    @Test
    void testProfileImageConstructor() {
        profileImage = new ProfileImage(PROFILE_IMAGE_ID, PROFILE_IMAGE_FILENAME);

        assertEquals(PROFILE_IMAGE_ID, profileImage.getId());
        assertEquals(PROFILE_IMAGE_FILENAME, profileImage.getFileName());
    }

    @DisplayName("프로필 이미지 Builder 패턴 - Domain")
    @Test
    void testBuilder() {
        profileImage = ProfileImage.builder()
                .fileName(PROFILE_IMAGE_FILENAME)
                .build();

        assertEquals(PROFILE_IMAGE_FILENAME, profileImage.getFileName());
    }

    @DisplayName("프로필 이미지 create 메서드 - Domain")
    @Test
    void testCreate() {
        profileImage = ProfileImage.create(PROFILE_IMAGE_FILENAME);

        assertEquals(PROFILE_IMAGE_FILENAME, profileImage.getFileName());
    }

    @DisplayName("프로필 이미지 UpdateProfileImage - Domain")
    @Test
    void testUpdateProfileImage() {
        String oldProfileImageFilename = profileImage.getFileName();

        profileImage.updateProfileImage(NEW_PROFILE_IMAGE_FILENAME);
        String newProfileImageFileName = profileImage.getFileName();

        assertNotEquals(oldProfileImageFilename, newProfileImageFileName);
        assertEquals(NEW_PROFILE_IMAGE_FILENAME, newProfileImageFileName);
    }

}