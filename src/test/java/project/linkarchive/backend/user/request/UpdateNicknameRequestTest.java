package project.linkarchive.backend.user.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.UserSetUpData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.linkarchive.backend.util.constant.Constants.NEW_NICKNAME;

class UpdateNicknameRequestTest extends UserSetUpData {

    @BeforeEach
    public void setup() {
        setUpUpdateNicknameRequest();
    }

    @DisplayName("UpdateNicknameRequestTest - DTO")
    @Test
    void testUpdateNicknameRequest() {
        assertEquals(NEW_NICKNAME, updateNicknameRequest.getNickname());
    }

}