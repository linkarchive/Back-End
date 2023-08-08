package project.linkarchive.backend.link.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.linkarchive.backend.util.constant.Constants.*;

class LinkMetaDataResponseTest extends SetUpMockData {

    @BeforeEach
    void setUp() {
        setUpLinkMetaDataResponse();
    }

    @DisplayName("LinkMetaDataResponseTest - DTO")
    @Test
    void testLinkMetaDataResponse() {
        assertEquals(META_TITLE, linkMetaDataResponse.getMetaTitle());
        assertEquals(META_DESCRIPTION, linkMetaDataResponse.getMetaDescription());
        assertEquals(META_THUMBNAIL, linkMetaDataResponse.getMetaThumbnail());
    }

}