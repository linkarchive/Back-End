package project.linkarchive.backend.user.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.UserSetUpData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.linkarchive.backend.util.constant.Constants.NEW_NICKNAME;

class UpdateNicknameResponseTest extends UserSetUpData {

    @BeforeEach
    public void setup() {
        setUpUser();
    }

    @DisplayName("UpdateNicknameResponse - DTO")
    @Test
    void testUpdateNicknameResponse() {
        setUpUpdateNicknameRequest();
        user.updateNickName(updateNicknameRequest);

        setUpUpdateNicknameResponse();
        String newNickname = updateNicknameResponse.getNickname();

        assertEquals(NEW_NICKNAME, newNickname);
    }

}