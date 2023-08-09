package project.linkarchive.backend.link.response.trash;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.linkarchive.backend.util.constant.Constants.*;

class TrashLinkResponseTest extends SetUpMockData {

    @BeforeEach
    void setUp() {
        setUpTrashLinkResponse();
    }

    @DisplayName("TrashLinkResponse Getter - DTO")
    @Test
    void testTrashLinkResponse() {
        assertEquals(LINK_ID, trashLinkResponse.getLinkId());
        assertEquals(URL, trashLinkResponse.getUrl());
        assertEquals(TITLE, trashLinkResponse.getTitle());
        assertEquals(DESCRIPTION, trashLinkResponse.getDescription());
        assertEquals(THUMBNAIL, trashLinkResponse.getThumbnail());
        assertEquals(BOOKMARK_COUNT, trashLinkResponse.getBookMarkCount());
        assertEquals(CREATED_AT, trashLinkResponse.getLinkCreatedTime());
    }

    @DisplayName("TrashLinkResponse Constructor - DTO")
    @Test
    void testConstructor() {
        trashLinkResponse = new TrashLinkResponse(LINK_ID, URL, TITLE, DESCRIPTION, THUMBNAIL, BOOKMARK_COUNT, CREATED_AT);

        assertEquals(LINK_ID, trashLinkResponse.getLinkId());
        assertEquals(URL, trashLinkResponse.getUrl());
        assertEquals(TITLE, trashLinkResponse.getTitle());
        assertEquals(DESCRIPTION, trashLinkResponse.getDescription());
        assertEquals(THUMBNAIL, trashLinkResponse.getThumbnail());
        assertEquals(BOOKMARK_COUNT, trashLinkResponse.getBookMarkCount());
        assertEquals(CREATED_AT, trashLinkResponse.getLinkCreatedTime());
    }

}