package project.linkarchive.backend.link.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.LinkSetUpData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.linkarchive.backend.util.constant.Constants.*;

class CreateLinkRequestTest extends LinkSetUpData {

    @DisplayName("CreateLinkRequestTest - DTO")
    @Test
    void testCreateLinkRequest() {
        assertEquals(URL, createLinkRequest.getUrl());
        assertEquals(TITLE, createLinkRequest.getTitle());
        assertEquals(DESCRIPTION, createLinkRequest.getDescription());
        assertEquals(THUMBNAIL, createLinkRequest.getThumbnail());
        assertEquals(tagList, createLinkRequest.getTags());
    }

}