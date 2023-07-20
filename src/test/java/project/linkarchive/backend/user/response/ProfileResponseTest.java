package project.linkarchive.backend.user.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.linkarchive.backend.util.constant.Constants.EMPTY;
import static project.linkarchive.backend.util.constant.Constants.PRE_SIGNED_URL;

class ProfileResponseTest extends SetUpMockData {

    @DisplayName("ProfileResponse - DTO")
    @Test
    void testProfileResponse() {
        setUpUser();
        setUpProfileResponse();

        assertEquals(EMPTY, profileResponse.getNickname());
        assertEquals(EMPTY, profileResponse.getIntroduce());
        assertEquals(PRE_SIGNED_URL, profileResponse.getProfileImageFileName());
    }

}