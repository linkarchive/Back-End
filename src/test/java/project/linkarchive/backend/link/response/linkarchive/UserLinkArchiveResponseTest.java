package project.linkarchive.backend.link.response.linkarchive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.linkarchive.backend.util.constant.Constants.HAS_NEXT;

class UserLinkArchiveResponseTest extends SetUpMockData {

    @BeforeEach
    void setUp() {
        setUpUserLinkArchiveResponse();
    }

    @DisplayName("UserLinkArchiveResponseTest - DTO")
    @Test
    void testUserLinkArchiveResponse() {
        assertEquals(userArchiveResponseList, userLinkArchiveResponse.getLinkArchive());
        assertEquals(HAS_NEXT, userLinkArchiveResponse.getHasNext());
    }

}