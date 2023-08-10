package project.linkarchive.backend.user.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.linkarchive.backend.util.constant.Constants.NEW_NICKNAME;

class UpdateNicknameRequestTest extends SetUpMockData {

    @BeforeEach
    void setUp() {
        setUpUpdateNicknameRequest();
    }

    @DisplayName("UpdateNicknameRequest Getter - DTO")
    @Test
    void testGetter() {
        assertEquals(NEW_NICKNAME, updateNicknameRequest.getNickname());
    }

    @DisplayName("UpdateNicknameRequest Constructor - DTO")
    @Test
    void testConstructor() {
        updateNicknameRequest = new UpdateNicknameRequest(NEW_NICKNAME);

        assertEquals(NEW_NICKNAME, updateNicknameRequest.getNickname());
    }

}