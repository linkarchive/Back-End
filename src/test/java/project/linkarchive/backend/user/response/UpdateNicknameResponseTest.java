package project.linkarchive.backend.user.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.linkarchive.backend.util.constant.Constants.NEW_NICKNAME;

class UpdateNicknameResponseTest extends SetUpMockData {

    @BeforeEach
    void setUp() {
        setUpUser();
        setUpUpdateNicknameResponse();
    }

    @DisplayName("UpdateNicknameResponse Getter - DTO")
    @Test
    void testGetter() {
        assertEquals(NEW_NICKNAME, updateNicknameResponse.getNickname());
    }

    @DisplayName("UpdateNicknameResponse Constructor - DTO")
    @Test
    void testConstructor() {
        updateNicknameResponse = new UpdateNicknameResponse(NEW_NICKNAME);

        assertEquals(NEW_NICKNAME, updateNicknameResponse.getNickname());
    }

}