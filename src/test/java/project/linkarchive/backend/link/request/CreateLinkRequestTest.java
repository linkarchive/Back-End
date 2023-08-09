package project.linkarchive.backend.link.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.linkarchive.backend.util.constant.Constants.*;

class CreateLinkRequestTest extends SetUpMockData {

    @BeforeEach
    void setUp() {
        setUpCreateLinkRequest();
    }

    @DisplayName("CreateLinkRequest Getter - DTO")
    @Test
    void testCreateLinkRequest() {
        assertEquals(URL, createLinkRequest.getUrl());
        assertEquals(TITLE, createLinkRequest.getTitle());
        assertEquals(DESCRIPTION, createLinkRequest.getDescription());
        assertEquals(THUMBNAIL, createLinkRequest.getThumbnail());
        assertEquals(tagList, createLinkRequest.getTagList());
    }

    @DisplayName("CreateLinkRequest Constructor - DTO")
    @Test
    void testConstructor() {
        createLinkRequest = new CreateLinkRequest(URL, TITLE, DESCRIPTION, THUMBNAIL, tagList);

        assertEquals(URL, createLinkRequest.getUrl());
        assertEquals(TITLE, createLinkRequest.getTitle());
        assertEquals(DESCRIPTION, createLinkRequest.getDescription());
        assertEquals(THUMBNAIL, createLinkRequest.getThumbnail());
        assertEquals(tagList, createLinkRequest.getTagList());
    }

}