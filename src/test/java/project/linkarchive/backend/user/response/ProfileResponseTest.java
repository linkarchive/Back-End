package project.linkarchive.backend.user.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.linkarchive.backend.util.constant.Constants.*;

class ProfileResponseTest extends SetUpMockData {

    @BeforeEach
    void setUp() {
        setUpUser();
        setUpProfileResponse();
    }

    @DisplayName("ProfileResponse - DTO")
    @Test
    void testProfileResponse() {
        assertEquals(USER_ID, profileResponse.getId());
        assertEquals(EMPTY, profileResponse.getNickname());
        assertEquals(EMPTY, profileResponse.getIntroduce());
        assertEquals(PRE_SIGNED_URL, profileResponse.getProfileImageFileName());
        assertEquals(FOLLOWER_COUNT, profileResponse.getFollowerCount());
        assertEquals(FOLLOWING_COUNT, profileResponse.getFollowingCount());
    }

}