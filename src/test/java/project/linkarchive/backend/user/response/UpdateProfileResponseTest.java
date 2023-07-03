package project.linkarchive.backend.user.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.UserSetUpData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.linkarchive.backend.util.constant.Constants.NEW_INTRODUCE;
import static project.linkarchive.backend.util.constant.Constants.NEW_NICKNAME;

class UpdateProfileResponseTest extends UserSetUpData {

    @BeforeEach
    public void setup() {
        setUpUser();
    }

    @DisplayName("UpdateProfileResponse - DTO")
    @Test
    void testUpdateProfileResponse() {
        setUpUpdateProfileRequest();
        user.updateProfile(updateProfileRequest);

        setUpUpdateProfileResponse();
        String newNickname = updateProfileResponse.getNickname();
        String newIntroduce = updateProfileResponse.getIntroduce();

        assertEquals(NEW_NICKNAME, newNickname);
        assertEquals(NEW_INTRODUCE, newIntroduce);
    }

}