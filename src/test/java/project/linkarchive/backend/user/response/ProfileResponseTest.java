package project.linkarchive.backend.user.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.UserSetUpData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.linkarchive.backend.util.constant.Constants.EMPTY;
import static project.linkarchive.backend.util.constant.Constants.PRE_SIGNED_URL;

class ProfileResponseTest extends UserSetUpData {

    @BeforeEach
    public void setup() {
        setUpUser();
        setUpProfileResponse();
    }

    @DisplayName("ProfileResponse - DTO")
    @Test
    void testProfileResponse() {
        assertEquals(EMPTY, profileResponse.getNickname());
        assertEquals(EMPTY, profileResponse.getIntroduce());
        assertEquals(PRE_SIGNED_URL, profileResponse.getProfileImageFileName());
    }

}