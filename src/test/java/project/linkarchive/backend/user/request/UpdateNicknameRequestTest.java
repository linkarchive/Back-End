package project.linkarchive.backend.user.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static project.linkarchive.backend.util.constant.Constants.NICKNAME;

class UpdateNicknameRequestTest {

    @DisplayName("UpdateNicknameRequestTest - DTO")
    @Test
    void testUpdateNicknameRequestTest() {
        UpdateNicknameRequest updateNicknameRequest = new UpdateNicknameRequest(NICKNAME);

        assertNotNull(updateNicknameRequest);
        assertEquals(NICKNAME, updateNicknameRequest.getNickname());
    }

}