package project.linkarchive.backend.user.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.UserSetUpData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static project.linkarchive.backend.util.constant.Constants.*;

class UpdateProfileRequestTest extends UserSetUpData {

    @DisplayName("UpdateProfileResponse - DTO")
    @Test
    void testUpdateProfileResponse() {
        assertEquals(NEW_NICKNAME, updateProfileRequest.getNickname());
        assertEquals(NEW_INTRODUCE, updateProfileRequest.getIntroduce());
    }

}