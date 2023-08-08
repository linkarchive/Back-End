package project.linkarchive.backend.link.response.linkList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.linkarchive.backend.util.constant.Constants.HAS_NEXT;

class UserLinkListResponseTest extends SetUpMockData {

    @BeforeEach
    void setUp() {
        setUpUserLinkListResponse();
    }

    @DisplayName("UserLinkListResponseTest - DTO")
    @Test
    void testUserLinkListResponse() {
        assertEquals(userLinkResponseList, userLinkListResponse.getLinkList());
        assertEquals(HAS_NEXT, userLinkListResponse.getHasNext());
    }

}