package project.linkarchive.backend.link.response.linkarchive;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.LinkSetUpData;

import static org.junit.jupiter.api.Assertions.*;
import static project.linkarchive.backend.util.constant.Constants.HAS_NEXT;

class UserLinkArchiveResponseTest extends LinkSetUpData {

    @DisplayName("UserLinkArchiveResponseTest - DTO")
    @Test
    void testUserLinkArchiveResponse() {
        assertEquals(userArchiveResponseList, userLinkArchiveResponse.getLinkArchive());
        assertEquals(HAS_NEXT, userLinkArchiveResponse.getHasNext());
    }

}