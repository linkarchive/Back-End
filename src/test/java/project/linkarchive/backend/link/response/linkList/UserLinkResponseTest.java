package project.linkarchive.backend.link.response.linkList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.LinkSetUpData;

import static org.junit.jupiter.api.Assertions.*;
import static project.linkarchive.backend.util.constant.Constants.*;

class UserLinkResponseTest extends LinkSetUpData {

    @DisplayName("UserLinkResponseTest - DTO")
    @Test
    void testUserLinkResponse() {
        assertEquals(URL, getUserLinkResponse.getUrl());
        assertEquals(TITLE, getUserLinkResponse.getTitle());
        assertEquals(DESCRIPTION, getUserLinkResponse.getDescription());
        assertEquals(THUMBNAIL, getUserLinkResponse.getThumbnail());
        assertEquals(BOOKMARK_COUNT, getUserLinkResponse.getBookMarkCount());
        assertEquals(CREATED_AT, getUserLinkResponse.getLinkCreatedTime());
        assertEquals(IS_READ, getUserLinkResponse.getIsRead());
        assertEquals(IS_MARK, getUserLinkResponse.getIsMark());
        assertEquals(tagResponseList, getUserLinkResponse.getTagList());
    }

    @DisplayName("UserLinkResponse Builder 패턴 - DTO")
    @Test
    void testBuilder() {
        UserLinkResponse getUserLinkResponse = UserLinkResponse.builder()
                .linkId(ID)
                .url(URL)
                .title(TITLE)
                .description(DESCRIPTION)
                .thumbnail(THUMBNAIL)
                .bookMarkCount(BOOKMARK_COUNT)
                .linkCreatedTime(CREATED_AT)
                .isRead(IS_READ)
                .isMark(IS_MARK)
                .tagList(tagResponseList)
                .build();

        assertEquals(ID, getUserLinkResponse.getLinkId());
        assertEquals(URL, getUserLinkResponse.getUrl());
        assertEquals(TITLE, getUserLinkResponse.getTitle());
        assertEquals(DESCRIPTION, getUserLinkResponse.getDescription());
        assertEquals(THUMBNAIL, getUserLinkResponse.getThumbnail());
        assertEquals(BOOKMARK_COUNT, getUserLinkResponse.getBookMarkCount());
        assertEquals(CREATED_AT, getUserLinkResponse.getLinkCreatedTime());
        assertEquals(IS_READ, getUserLinkResponse.getIsRead());
        assertEquals(IS_MARK, getUserLinkResponse.getIsMark());
        assertEquals(tagResponseList, getUserLinkResponse.getTagList());
    }

    @DisplayName("UserLinkResponse create method - DTO")
    @Test
    void testCreate() {
        UserLinkResponse getUserLinkResponse = UserLinkResponse.create(linkResponse, IS_READ, IS_MARK, tagResponseList);

        assertEquals(ID, getUserLinkResponse.getLinkId());
        assertEquals(URL, getUserLinkResponse.getUrl());
        assertEquals(TITLE, getUserLinkResponse.getTitle());
        assertEquals(DESCRIPTION, getUserLinkResponse.getDescription());
        assertEquals(THUMBNAIL, getUserLinkResponse.getThumbnail());
        assertEquals(BOOKMARK_COUNT, getUserLinkResponse.getBookMarkCount());
        assertEquals(CREATED_AT, getUserLinkResponse.getLinkCreatedTime());
        assertEquals(IS_READ, getUserLinkResponse.getIsRead());
        assertEquals(IS_MARK, getUserLinkResponse.getIsMark());
        assertEquals(tagResponseList, getUserLinkResponse.getTagList());
    }

}