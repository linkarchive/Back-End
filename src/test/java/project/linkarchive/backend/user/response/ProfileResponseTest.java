package project.linkarchive.backend.user.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.UserSetUpData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static project.linkarchive.backend.util.constant.Constants.*;

class ProfileResponseTest extends UserSetUpData {

    @DisplayName("ProfileResponse - DTO")
    @Test
    void testProfileResponse() {
        ProfileResponse profileResponse = new ProfileResponse(user, PROFILE_IMAGE_FILENAME);

        assertNotNull(profileResponse);
        assertEquals(NICKNAME, profileResponse.getNickname());
        assertEquals(INTRODUCE, profileResponse.getIntroduce());
        assertEquals(PROFILE_IMAGE_FILENAME, profileResponse.getProfileImageFileName());
    }

}