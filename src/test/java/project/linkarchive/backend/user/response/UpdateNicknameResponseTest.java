package project.linkarchive.backend.user.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static project.linkarchive.backend.util.constant.Constants.NICKNAME;

class UpdateNicknameResponseTest {

    @DisplayName("UpdateNicknameResponse - DTO")
    @Test
    void testUpdateNicknameResponse() {
        UpdateNicknameResponse updateNicknameResponse = new UpdateNicknameResponse(NICKNAME);

        assertNotNull(updateNicknameResponse);
        assertEquals(NICKNAME, updateNicknameResponse.getNickname());
    }

}