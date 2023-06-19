package project.linkarchive.backend.user.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.UserSetUpData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static project.linkarchive.backend.util.constant.Constants.INTRODUCE;
import static project.linkarchive.backend.util.constant.Constants.NICKNAME;

class UpdateProfileResponseTest extends UserSetUpData {

    @DisplayName("UpdateProfileResponse - DTO")
    @Test
    void testUpdateProfileResponse() {
        UpdateProfileResponse updateProfileResponse = new UpdateProfileResponse(user);

        assertNotNull(updateProfileResponse);
        assertEquals(NICKNAME, updateProfileResponse.getNickname());
        assertEquals(INTRODUCE, updateProfileResponse.getIntroduce());
    }

}