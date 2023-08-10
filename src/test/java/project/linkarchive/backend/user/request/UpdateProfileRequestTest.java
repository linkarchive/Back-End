package project.linkarchive.backend.user.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.linkarchive.backend.util.constant.Constants.NEW_INTRODUCE;
import static project.linkarchive.backend.util.constant.Constants.NEW_NICKNAME;

class UpdateProfileRequestTest extends SetUpMockData {

    @BeforeEach
    void setUp() {
        setUpUpdateProfileRequest();
    }

    @DisplayName("UpdateProfileResponse Getter - DTO")
    @Test
    void testGetter() {
        assertEquals(NEW_NICKNAME, updateProfileRequest.getNickname());
        assertEquals(NEW_INTRODUCE, updateProfileRequest.getIntroduce());
    }

    @DisplayName("UpdateProfileResponse Constructor - DTO")
    @Test
    void testConstructor() {
        updateProfileRequest = new UpdateProfileRequest(NEW_NICKNAME, NEW_INTRODUCE);

        assertEquals(NEW_NICKNAME, updateProfileRequest.getNickname());
        assertEquals(NEW_INTRODUCE, updateProfileRequest.getIntroduce());
    }


}