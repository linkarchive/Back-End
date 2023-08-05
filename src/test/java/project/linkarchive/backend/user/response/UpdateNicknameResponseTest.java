package project.linkarchive.backend.user.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static project.linkarchive.backend.util.constant.Constants.NEW_NICKNAME;

class UpdateNicknameResponseTest extends SetUpMockData {

    @BeforeEach
    void setUp() {
        setUpUser();
        setUpUpdateNicknameRequest();
    }

    @DisplayName("UpdateNicknameResponse - DTO")
    @Test
    void testUpdateNicknameResponse() {
        String oldNickname = user.getNickname();
        user.updateNickName(updateNicknameRequest);

        setUpUpdateNicknameResponse();
        String newNickname = updateNicknameResponse.getNickname();

        assertNotEquals(oldNickname, newNickname);
        assertEquals(NEW_NICKNAME, newNickname);
    }

}