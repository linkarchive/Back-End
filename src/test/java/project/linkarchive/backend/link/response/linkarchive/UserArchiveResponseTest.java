package project.linkarchive.backend.link.response.linkarchive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.linkarchive.backend.util.constant.Constants.*;

class UserArchiveResponseTest extends SetUpMockData {

    @BeforeEach
    void setUp() {
        setUpUserArchiveResponse();
    }

    @DisplayName("UserArchiveResponse Getter - DTO")
    @Test
    void testUserArchiveResponse() {
        assertEquals(USER_ID, userArchiveResponse.getUserId());
        assertEquals(NICKNAME, userArchiveResponse.getNickname());
        assertEquals(PROFILE_IMAGE_FILENAME, userArchiveResponse.getProfileImage());
        assertEquals(LINK_ID, userArchiveResponse.getLinkId());
        assertEquals(URL, userArchiveResponse.getUrl());
        assertEquals(TITLE, userArchiveResponse.getTitle());
        assertEquals(DESCRIPTION, userArchiveResponse.getDescription());
        assertEquals(THUMBNAIL, userArchiveResponse.getThumbnail());
        assertEquals(BOOKMARK_COUNT, userArchiveResponse.getBookMarkCount());
        assertEquals(IS_READ, userArchiveResponse.getIsRead());
        assertEquals(IS_MARK, userArchiveResponse.getIsMark());
        assertEquals(tagResponseList, userArchiveResponse.getTagList());
        assertEquals(CREATED_AT, userArchiveResponse.getLinkCreatedTime());
        assertEquals(UPDATED_AT, userArchiveResponse.getLinkUpdatedTime());
    }

    @DisplayName("UserArchiveResponse Constructor - DTO")
    @Test
    void testConstructor() {
        userArchiveResponse = new UserArchiveResponse(USER_ID, NICKNAME, PROFILE_IMAGE_FILENAME, LINK_ID, URL, TITLE, DESCRIPTION, THUMBNAIL, BOOKMARK_COUNT, IS_READ, IS_MARK, tagResponseList, CREATED_AT, UPDATED_AT);

        assertEquals(USER_ID, userArchiveResponse.getUserId());
        assertEquals(NICKNAME, userArchiveResponse.getNickname());
        assertEquals(PROFILE_IMAGE_FILENAME, userArchiveResponse.getProfileImage());
        assertEquals(LINK_ID, userArchiveResponse.getLinkId());
        assertEquals(URL, userArchiveResponse.getUrl());
        assertEquals(TITLE, userArchiveResponse.getTitle());
        assertEquals(DESCRIPTION, userArchiveResponse.getDescription());
        assertEquals(THUMBNAIL, userArchiveResponse.getThumbnail());
        assertEquals(BOOKMARK_COUNT, userArchiveResponse.getBookMarkCount());
        assertEquals(IS_READ, userArchiveResponse.getIsRead());
        assertEquals(IS_MARK, userArchiveResponse.getIsMark());
        assertEquals(tagResponseList, userArchiveResponse.getTagList());
        assertEquals(CREATED_AT, userArchiveResponse.getLinkCreatedTime());
        assertEquals(UPDATED_AT, userArchiveResponse.getLinkUpdatedTime());
    }

    @DisplayName("UserArchiveResponse Builder Pattern - DTO")
    @Test
    void testBuilder() {
        userArchiveResponse = UserArchiveResponse.builder()
                .userId(USER_ID)
                .nickname(NICKNAME)
                .profileImage(PROFILE_IMAGE_FILENAME)
                .linkId(LINK_ID)
                .url(URL)
                .title(TITLE)
                .description(DESCRIPTION)
                .thumbnail(THUMBNAIL)
                .bookMarkCount(BOOKMARK_COUNT)
                .isRead(IS_READ)
                .isMark(IS_MARK)
                .tagList(tagResponseList)
                .linkCreatedTime(CREATED_AT)
                .linkUpdatedTime(UPDATED_AT)
                .build();

        assertEquals(USER_ID, userArchiveResponse.getUserId());
        assertEquals(NICKNAME, userArchiveResponse.getNickname());
        assertEquals(PROFILE_IMAGE_FILENAME, userArchiveResponse.getProfileImage());
        assertEquals(LINK_ID, userArchiveResponse.getLinkId());
        assertEquals(URL, userArchiveResponse.getUrl());
        assertEquals(TITLE, userArchiveResponse.getTitle());
        assertEquals(DESCRIPTION, userArchiveResponse.getDescription());
        assertEquals(THUMBNAIL, userArchiveResponse.getThumbnail());
        assertEquals(BOOKMARK_COUNT, userArchiveResponse.getBookMarkCount());
        assertEquals(IS_READ, userArchiveResponse.getIsRead());
        assertEquals(IS_MARK, userArchiveResponse.getIsMark());
        assertEquals(tagResponseList, userArchiveResponse.getTagList());
        assertEquals(CREATED_AT, userArchiveResponse.getLinkCreatedTime());
        assertEquals(UPDATED_AT, userArchiveResponse.getLinkUpdatedTime());
    }

    @DisplayName("UserArchiveResponse Create Method - DTO")
    @Test
    void testCreate() {
        setUpArchiveResponse();

        userArchiveResponse = UserArchiveResponse.create(archiveResponse, PRE_SIGNED_URL, IS_READ, IS_MARK, tagResponseList);

        assertEquals(USER_ID, userArchiveResponse.getUserId());
        assertEquals(NICKNAME, userArchiveResponse.getNickname());
        assertEquals(PRE_SIGNED_URL, userArchiveResponse.getProfileImage());
        assertEquals(LINK_ID, userArchiveResponse.getLinkId());
        assertEquals(URL, userArchiveResponse.getUrl());
        assertEquals(TITLE, userArchiveResponse.getTitle());
        assertEquals(DESCRIPTION, userArchiveResponse.getDescription());
        assertEquals(THUMBNAIL, userArchiveResponse.getThumbnail());
        assertEquals(BOOKMARK_COUNT, userArchiveResponse.getBookMarkCount());
        assertEquals(IS_READ, userArchiveResponse.getIsRead());
        assertEquals(IS_MARK, userArchiveResponse.getIsMark());
        assertEquals(tagResponseList, userArchiveResponse.getTagList());
        assertEquals(CREATED_AT, userArchiveResponse.getLinkCreatedTime());
        assertEquals(UPDATED_AT, userArchiveResponse.getLinkUpdatedTime());
    }

}