package project.linkarchive.backend.link.response.linkarchive;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.LinkSetUpData;

import static org.junit.jupiter.api.Assertions.*;
import static project.linkarchive.backend.util.constant.Constants.*;
import static project.linkarchive.backend.util.constant.Constants.BOOKMARK_COUNT;

class UserArchiveResponseTest extends LinkSetUpData {

    @DisplayName("UserArchiveResponseTest - DTO")
    @Test
    void testUserArchiveResponse() {
        assertEquals(NICKNAME, userArchiveResponse.getNickname());
        assertEquals(PROFILE_IMAGE_FILENAME, userArchiveResponse.getProfileImage());
        assertEquals(URL, userArchiveResponse.getUrl());
        assertEquals(TITLE, userArchiveResponse.getTitle());
        assertEquals(DESCRIPTION, userArchiveResponse.getDescription());
        assertEquals(THUMBNAIL, userArchiveResponse.getThumbnail());
        assertEquals(CREATED_AT, userArchiveResponse.getLinkCreatedTime());
        assertEquals(BOOKMARK_COUNT, userArchiveResponse.getBookMarkCount());
        assertEquals(IS_READ, userArchiveResponse.getIsRead());
        assertEquals(IS_MARK, userArchiveResponse.getIsMark());
        assertEquals(tagResponseList, userArchiveResponse.getTagList());
    }

    @DisplayName("UserArchiveResponse Builder 패턴 - DTO")
    @Test
    void testBuilder() {
        UserArchiveResponse getUserArchiveResponse = UserArchiveResponse.builder()
                .nickname(NICKNAME)
                .profileImage(PROFILE_IMAGE_FILENAME)
                .url(URL)
                .title(TITLE)
                .description(DESCRIPTION)
                .thumbnail(THUMBNAIL)
                .linkCreatedTime(CREATED_AT)
                .bookMarkCount(BOOKMARK_COUNT)
                .isRead(IS_READ)
                .isMark(IS_MARK)
                .tagList(tagResponseList)
                .build();

        assertEquals(NICKNAME, getUserArchiveResponse.getNickname());
        assertEquals(PROFILE_IMAGE_FILENAME, getUserArchiveResponse.getProfileImage());
        assertEquals(URL, getUserArchiveResponse.getUrl());
        assertEquals(TITLE, getUserArchiveResponse.getTitle());
        assertEquals(DESCRIPTION, getUserArchiveResponse.getDescription());
        assertEquals(THUMBNAIL, getUserArchiveResponse.getThumbnail());
        assertEquals(CREATED_AT, getUserArchiveResponse.getLinkCreatedTime());
        assertEquals(BOOKMARK_COUNT, getUserArchiveResponse.getBookMarkCount());
        assertEquals(IS_READ, getUserArchiveResponse.getIsRead());
        assertEquals(IS_MARK, getUserArchiveResponse.getIsMark());
        assertEquals(tagResponseList, getUserArchiveResponse.getTagList());
    }

    @DisplayName("UserArchiveResponse create method - DTO")
    @Test
    void testCreate() {
        UserArchiveResponse getUserArchiveResponse = UserArchiveResponse.create(archiveResponse, PRE_SIGNED_URL, IS_READ, IS_MARK, tagResponseList);

        assertEquals(NICKNAME, getUserArchiveResponse.getNickname());
        assertEquals(PRE_SIGNED_URL, getUserArchiveResponse.getProfileImage());
        assertEquals(URL, getUserArchiveResponse.getUrl());
        assertEquals(TITLE, getUserArchiveResponse.getTitle());
        assertEquals(DESCRIPTION, getUserArchiveResponse.getDescription());
        assertEquals(THUMBNAIL, getUserArchiveResponse.getThumbnail());
        assertEquals(CREATED_AT, getUserArchiveResponse.getLinkCreatedTime());
        assertEquals(BOOKMARK_COUNT, getUserArchiveResponse.getBookMarkCount());
        assertEquals(IS_READ, getUserArchiveResponse.getIsRead());
        assertEquals(IS_MARK, getUserArchiveResponse.getIsMark());
        assertEquals(tagResponseList, getUserArchiveResponse.getTagList());
    }

}