package project.linkarchive.backend.user.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static project.linkarchive.backend.util.constant.Constants.INTRODUCE;
import static project.linkarchive.backend.util.constant.Constants.NICKNAME;

class UpdateProfileRequestTest {

    @DisplayName("UpdateProfileResponse - DTO")
    @Test
    void testUpdateProfileResponse() {
        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest(NICKNAME, INTRODUCE);

        assertNotNull(updateProfileRequest);
        assertEquals(NICKNAME, updateProfileRequest.getNickname());
        assertEquals(INTRODUCE, updateProfileRequest.getIntroduce());
    }

}