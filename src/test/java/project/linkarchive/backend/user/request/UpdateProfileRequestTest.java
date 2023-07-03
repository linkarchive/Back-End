package project.linkarchive.backend.user.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.UserSetUpData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.linkarchive.backend.util.constant.Constants.NEW_INTRODUCE;
import static project.linkarchive.backend.util.constant.Constants.NEW_NICKNAME;

class UpdateProfileRequestTest extends UserSetUpData {

    @BeforeEach
    public void setup() {
        setUpUpdateProfileRequest();
    }

    @DisplayName("UpdateProfileResponse - DTO")
    @Test
    void testUpdateProfileResponse() {
        assertEquals(NEW_NICKNAME, updateProfileRequest.getNickname());
        assertEquals(NEW_INTRODUCE, updateProfileRequest.getIntroduce());
    }

}