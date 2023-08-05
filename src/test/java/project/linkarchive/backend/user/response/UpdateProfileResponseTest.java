package project.linkarchive.backend.user.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static project.linkarchive.backend.util.constant.Constants.NEW_INTRODUCE;
import static project.linkarchive.backend.util.constant.Constants.NEW_NICKNAME;

class UpdateProfileResponseTest extends SetUpMockData {

    @BeforeEach
    void setUp() {
        setUpUser();
        setUpUpdateProfileRequest();
    }

    @DisplayName("UpdateProfileResponse - DTO")
    @Test
    void testUpdateProfileResponse() {
        String oldNickname = user.getNickname();
        String oldIntroduce = user.getIntroduce();
        user.updateProfile(updateProfileRequest);

        setUpUpdateProfileResponse();
        String newNickname = updateProfileResponse.getNickname();
        String newIntroduce = updateProfileResponse.getIntroduce();

        assertNotEquals(oldNickname, newNickname);
        assertNotEquals(oldIntroduce, newIntroduce);
        assertEquals(NEW_NICKNAME, newNickname);
        assertEquals(NEW_INTRODUCE, newIntroduce);
    }

}