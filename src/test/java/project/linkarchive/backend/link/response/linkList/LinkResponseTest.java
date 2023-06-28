package project.linkarchive.backend.link.response.linkList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.LinkSetUpData;

import static org.junit.jupiter.api.Assertions.*;
import static project.linkarchive.backend.util.constant.Constants.*;

class LinkResponseTest extends LinkSetUpData {

    @DisplayName("LinkResponseTest - DTO")
    @Test
    void testLinkResponse() {
        assertEquals(ID, linkResponse.getLinkId());
        assertEquals(URL, linkResponse.getUrl());
        assertEquals(TITLE, linkResponse.getTitle());
        assertEquals(DESCRIPTION, linkResponse.getDescription());
        assertEquals(THUMBNAIL, linkResponse.getThumbnail());
        assertEquals(BOOKMARK_COUNT, linkResponse.getBookMarkCount());
        assertEquals(CREATED_AT, linkResponse.getLinkCreatedTime());
    }

}