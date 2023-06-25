package project.linkarchive.backend.user.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.UserSetUpData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static project.linkarchive.backend.util.constant.Constants.NICKNAME;

class UpdateNicknameRequestTest extends UserSetUpData {

    @DisplayName("UpdateNicknameRequestTest - DTO")
    @Test
    void testUpdateNicknameRequest() {
        assertEquals(NICKNAME, updateNicknameRequest.getNickname());
    }

}