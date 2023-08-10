package project.linkarchive.backend.link.response.trash;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.linkarchive.backend.util.constant.Constants.HAS_NEXT;

class UserTrashLinkListResponseTest extends SetUpMockData {

    @BeforeEach
    void setUp() {
        setUpUserTrashLinkListResponse();
    }

    @DisplayName("UserTrashLinkListResponse Getter - DTO")
    @Test
    void testUserTrashLinkListResponse() {
        assertEquals(trashLinkListResponseList, userTrashLinkListResponse.getLinkList());
        assertEquals(HAS_NEXT, userTrashLinkListResponse.getHasNext());
    }

    @DisplayName("UserTrashLinkListResponse Constructor - DTO")
    @Test
    void testConstructor() {
        userTrashLinkListResponse = new UserTrashLinkListResponse(trashLinkListResponseList, HAS_NEXT);

        assertEquals(trashLinkListResponseList, userTrashLinkListResponse.getLinkList());
        assertEquals(HAS_NEXT, userTrashLinkListResponse.getHasNext());
    }

}