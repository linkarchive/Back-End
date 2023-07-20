package project.linkarchive.backend.user.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.linkarchive.backend.util.constant.Constants.NEW_INTRODUCE;
import static project.linkarchive.backend.util.constant.Constants.NEW_NICKNAME;

class UpdateProfileRequestTest extends SetUpMockData {

    @DisplayName("UpdateProfileResponse - DTO")
    @Test
    void testUpdateProfileResponse() {
        setUpUpdateProfileRequest();

        assertEquals(NEW_NICKNAME, updateProfileRequest.getNickname());
        assertEquals(NEW_INTRODUCE, updateProfileRequest.getIntroduce());
    }

}