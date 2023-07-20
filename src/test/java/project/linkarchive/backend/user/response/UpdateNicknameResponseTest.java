package project.linkarchive.backend.user.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.linkarchive.backend.util.constant.Constants.NEW_NICKNAME;

class UpdateNicknameResponseTest extends SetUpMockData {

    @DisplayName("UpdateNicknameResponse - DTO")
    @Test
    void testUpdateNicknameResponse() {
        setUpUser();
        setUpUpdateNicknameRequest();
        user.updateNickName(updateNicknameRequest);

        setUpUpdateNicknameResponse();
        String newNickname = updateNicknameResponse.getNickname();

        assertEquals(NEW_NICKNAME, newNickname);
    }

}