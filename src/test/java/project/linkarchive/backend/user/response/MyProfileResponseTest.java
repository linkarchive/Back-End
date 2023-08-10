package project.linkarchive.backend.user.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.linkarchive.backend.util.constant.Constants.*;

class MyProfileResponseTest extends SetUpMockData {

    @BeforeEach
    void setUp() {
        setUpUser();
        setUpMyProfileResponse();
    }

    @DisplayName("MyProfileResponse Getter - DTO")
    @Test
    void testGetter() {
        assertEquals(USER_ID, myProfileResponse.getId());
        assertEquals(EMPTY, myProfileResponse.getNickname());
        assertEquals(EMPTY, myProfileResponse.getIntroduce());
        assertEquals(PRE_SIGNED_URL, myProfileResponse.getProfileImageFileName());
        assertEquals(FOLLOWER_COUNT, myProfileResponse.getFollowerCount());
        assertEquals(FOLLOWING_COUNT, myProfileResponse.getFollowingCount());
    }

    @DisplayName("MyProfileResponse Constructor - DTO")
    @Test
    void testConstructor() {
        myProfileResponse = new MyProfileResponse(user, PRE_SIGNED_URL);

        assertEquals(USER_ID, myProfileResponse.getId());
        assertEquals(EMPTY, myProfileResponse.getNickname());
        assertEquals(EMPTY, myProfileResponse.getIntroduce());
        assertEquals(PRE_SIGNED_URL, myProfileResponse.getProfileImageFileName());
        assertEquals(FOLLOWER_COUNT, myProfileResponse.getFollowerCount());
        assertEquals(FOLLOWING_COUNT, myProfileResponse.getFollowingCount());
    }

}