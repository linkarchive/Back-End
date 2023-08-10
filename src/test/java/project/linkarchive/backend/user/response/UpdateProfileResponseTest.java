package project.linkarchive.backend.user.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.linkarchive.backend.util.constant.Constants.EMPTY;

class UpdateProfileResponseTest extends SetUpMockData {

    @BeforeEach
    void setUp() {
        setUpUser();
        setUpUpdateProfileResponse();
    }

    @DisplayName("UpdateProfileResponse Getter - DTO")
    @Test
    void testGetter() {
        assertEquals(EMPTY, updateProfileResponse.getNickname());
        assertEquals(EMPTY, updateProfileResponse.getIntroduce());
    }

    @DisplayName("UpdateProfileResponse Constructor - DTO")
    @Test
    void testConstructor() {
        updateProfileResponse = new UpdateProfileResponse(user);

        assertEquals(EMPTY, updateProfileResponse.getNickname());
        assertEquals(EMPTY, updateProfileResponse.getIntroduce());
    }

}