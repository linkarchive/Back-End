package project.linkarchive.backend.link.response.linkarchive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.linkarchive.backend.util.constant.Constants.*;

class ArchiveResponseTest extends SetUpMockData {

    @BeforeEach
    void setUp() {
        setUpArchiveResponse();
    }

    @DisplayName("ArchiveResponse Getter - DTO")
    @Test
    void testArchiveResponse() {
        assertEquals(USER_ID, archiveResponse.getUserId());
        assertEquals(NICKNAME, archiveResponse.getNickname());
        assertEquals(PROFILE_IMAGE_FILENAME, archiveResponse.getProfileImage());
        assertEquals(LINK_ID, archiveResponse.getLinkId());
        assertEquals(URL, archiveResponse.getUrl());
        assertEquals(TITLE, archiveResponse.getTitle());
        assertEquals(DESCRIPTION, archiveResponse.getDescription());
        assertEquals(THUMBNAIL, archiveResponse.getThumbnail());
        assertEquals(BOOKMARK_COUNT, archiveResponse.getBookMarkCount());
        assertEquals(CREATED_AT, archiveResponse.getLinkCreatedTime());
        assertEquals(UPDATED_AT, archiveResponse.getLinkUpdatedTime());
    }

    @DisplayName("ArchiveResponse Constructor - DTO")
    @Test
    void testConstructor() {
        archiveResponse = new ArchiveResponse(USER_ID, NICKNAME, PROFILE_IMAGE_FILENAME, LINK_ID, URL, TITLE, DESCRIPTION, THUMBNAIL, BOOKMARK_COUNT, CREATED_AT, UPDATED_AT);

        assertEquals(USER_ID, archiveResponse.getUserId());
        assertEquals(NICKNAME, archiveResponse.getNickname());
        assertEquals(PROFILE_IMAGE_FILENAME, archiveResponse.getProfileImage());
        assertEquals(LINK_ID, archiveResponse.getLinkId());
        assertEquals(URL, archiveResponse.getUrl());
        assertEquals(TITLE, archiveResponse.getTitle());
        assertEquals(DESCRIPTION, archiveResponse.getDescription());
        assertEquals(THUMBNAIL, archiveResponse.getThumbnail());
        assertEquals(BOOKMARK_COUNT, archiveResponse.getBookMarkCount());
        assertEquals(CREATED_AT, archiveResponse.getLinkCreatedTime());
        assertEquals(UPDATED_AT, archiveResponse.getLinkUpdatedTime());
    }

}