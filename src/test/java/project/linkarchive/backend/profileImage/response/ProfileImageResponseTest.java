package project.linkarchive.backend.profileImage.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.linkarchive.backend.util.constant.Constants.PROFILE_IMAGE_FILENAME;

class ProfileImageResponseTest extends SetUpMockData {

    @BeforeEach
    void setUp() {
        setUpProfileImageResponse();
    }

    @DisplayName("ProfileImageResponse - DTO")
    @Test
    void testProfileImageResponse() {
        ProfileImageResponse profileImageResponse = new ProfileImageResponse(PROFILE_IMAGE_FILENAME);

        assertEquals(PROFILE_IMAGE_FILENAME, profileImageResponse.getProfileImageFileName());
    }

}