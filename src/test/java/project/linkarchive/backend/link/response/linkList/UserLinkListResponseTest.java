package project.linkarchive.backend.link.response.linkList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.LinkSetUpData;

import static org.junit.jupiter.api.Assertions.*;
import static project.linkarchive.backend.util.constant.Constants.HAS_NEXT;

class UserLinkListResponseTest extends LinkSetUpData {

    @DisplayName("UserLinkListResponseTest - DTO")
    @Test
    void testUserLinkListResponse() {
        assertEquals(userLinkResponseList, userLinkListResponse.getLinkList());
        assertEquals(HAS_NEXT, userLinkListResponse.getHasNext());
    }

}