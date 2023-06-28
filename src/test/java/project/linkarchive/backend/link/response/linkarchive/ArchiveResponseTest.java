package project.linkarchive.backend.link.response.linkarchive;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.LinkSetUpData;

import static org.junit.jupiter.api.Assertions.*;
import static project.linkarchive.backend.util.constant.Constants.*;

class ArchiveResponseTest extends LinkSetUpData {

    @DisplayName("ArchiveResponseTest - DTO")
    @Test
    void testArchiveResponse() {
        assertEquals(ID, archiveResponse.getUserId());
        assertEquals(NICKNAME, archiveResponse.getNickname());
        assertEquals(PROFILE_IMAGE_FILENAME, archiveResponse.getProfileImage());
        assertEquals(ID, archiveResponse.getLinkId());
        assertEquals(URL, archiveResponse.getUrl());
        assertEquals(TITLE, archiveResponse.getTitle());
        assertEquals(DESCRIPTION, archiveResponse.getDescription());
        assertEquals(THUMBNAIL, archiveResponse.getThumbnail());
        assertEquals(CREATED_AT, archiveResponse.getLinkCreatedTime());
        assertEquals(BOOKMARK_COUNT, archiveResponse.getBookMarkCount());
    }


}