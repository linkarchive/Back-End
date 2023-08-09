package project.linkarchive.backend.user.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.linkarchive.backend.util.constant.Constants.*;

class UserProfileResponseTest extends SetUpMockData {

    @BeforeEach
    void setUp() {
        setUpUser();
        setUpUserProfileResponse();
    }

    @DisplayName("UserProfileResponse Getter - DTO")
    @Test
    void testGetter() {
        assertEquals(USER_ID, userProfileResponse.getId());
        assertEquals(EMPTY, userProfileResponse.getNickname());
        assertEquals(EMPTY, userProfileResponse.getIntroduce());
        assertEquals(PRE_SIGNED_URL, userProfileResponse.getProfileImageFileName());
        assertEquals(FOLLOWER_COUNT, userProfileResponse.getFollowerCount());
        assertEquals(FOLLOWING_COUNT, userProfileResponse.getFollowingCount());
        assertEquals(IS_FOLLOW, userProfileResponse.getIsFollow());
    }

    @DisplayName("UserProfileResponse Constructor - DTO")
    @Test
    void testConstructor() {
        userProfileResponse = new UserProfileResponse(user, PRE_SIGNED_URL, IS_FOLLOW);

        assertEquals(USER_ID, userProfileResponse.getId());
        assertEquals(EMPTY, userProfileResponse.getNickname());
        assertEquals(EMPTY, userProfileResponse.getIntroduce());
        assertEquals(PRE_SIGNED_URL, userProfileResponse.getProfileImageFileName());
        assertEquals(FOLLOWER_COUNT, userProfileResponse.getFollowerCount());
        assertEquals(FOLLOWING_COUNT, userProfileResponse.getFollowingCount());
        assertEquals(IS_FOLLOW, userProfileResponse.getIsFollow());
    }

}