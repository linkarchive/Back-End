package project.linkarchive.backend.link.response.linkList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.linkarchive.backend.util.constant.Constants.*;

class LinkResponseTest extends SetUpMockData {

    @BeforeEach
    void setUp() {
        setUpLinkResponse();
    }

    @DisplayName("LinkResponseTest - DTO")
    @Test
    void testLinkResponse() {
        assertEquals(LINK_ID, linkResponse.getLinkId());
        assertEquals(URL, linkResponse.getUrl());
        assertEquals(TITLE, linkResponse.getTitle());
        assertEquals(DESCRIPTION, linkResponse.getDescription());
        assertEquals(THUMBNAIL, linkResponse.getThumbnail());
        assertEquals(BOOKMARK_COUNT, linkResponse.getBookMarkCount());
        assertEquals(CREATED_AT, linkResponse.getLinkCreatedTime());
        assertEquals(UPDATED_AT, linkResponse.getLinkUpdatedTime());
    }

}