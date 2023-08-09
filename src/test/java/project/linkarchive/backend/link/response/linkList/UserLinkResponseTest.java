package project.linkarchive.backend.link.response.linkList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.linkarchive.backend.util.constant.Constants.*;

class UserLinkResponseTest extends SetUpMockData {

    @BeforeEach
    void setUp() {
        setUpUserLinkResponse();
    }

    @DisplayName("UserLinkResponse Getter - DTO")
    @Test
    void testUserLinkResponse() {
        assertEquals(LINK_ID, userLinkResponse.getLinkId());
        assertEquals(URL, userLinkResponse.getUrl());
        assertEquals(TITLE, userLinkResponse.getTitle());
        assertEquals(DESCRIPTION, userLinkResponse.getDescription());
        assertEquals(THUMBNAIL, userLinkResponse.getThumbnail());
        assertEquals(BOOKMARK_COUNT, userLinkResponse.getBookMarkCount());
        assertEquals(IS_READ, userLinkResponse.getIsRead());
        assertEquals(IS_MARK, userLinkResponse.getIsMark());
        assertEquals(tagResponseList, userLinkResponse.getTagList());
        assertEquals(CREATED_AT, userLinkResponse.getLinkCreatedTime());
        assertEquals(UPDATED_AT, userLinkResponse.getLinkUpdatedTime());
    }

    @DisplayName("UserLinkResponse Constructor - DTO")
    @Test
    void testConstructor() {
        userLinkResponse = new UserLinkResponse(LINK_ID, URL, TITLE, DESCRIPTION, THUMBNAIL, BOOKMARK_COUNT, IS_READ, IS_MARK, tagResponseList, CREATED_AT, UPDATED_AT);

        assertEquals(LINK_ID, userLinkResponse.getLinkId());
        assertEquals(URL, userLinkResponse.getUrl());
        assertEquals(TITLE, userLinkResponse.getTitle());
        assertEquals(DESCRIPTION, userLinkResponse.getDescription());
        assertEquals(THUMBNAIL, userLinkResponse.getThumbnail());
        assertEquals(BOOKMARK_COUNT, userLinkResponse.getBookMarkCount());
        assertEquals(IS_READ, userLinkResponse.getIsRead());
        assertEquals(IS_MARK, userLinkResponse.getIsMark());
        assertEquals(tagResponseList, userLinkResponse.getTagList());
        assertEquals(CREATED_AT, userLinkResponse.getLinkCreatedTime());
        assertEquals(UPDATED_AT, userLinkResponse.getLinkUpdatedTime());
    }

    @DisplayName("UserLinkResponse Builder Pattern - DTO")
    @Test
    void testBuilder() {
        userLinkResponse = UserLinkResponse.builder()
                .linkId(LINK_ID)
                .url(URL)
                .title(TITLE)
                .description(DESCRIPTION)
                .thumbnail(THUMBNAIL)
                .bookMarkCount(BOOKMARK_COUNT)
                .linkCreatedTime(CREATED_AT)
                .isRead(IS_READ)
                .isMark(IS_MARK)
                .tagList(tagResponseList)
                .linkCreatedTime(CREATED_AT)
                .linkUpdatedTime(UPDATED_AT)
                .build();

        assertEquals(LINK_ID, userLinkResponse.getLinkId());
        assertEquals(URL, userLinkResponse.getUrl());
        assertEquals(TITLE, userLinkResponse.getTitle());
        assertEquals(DESCRIPTION, userLinkResponse.getDescription());
        assertEquals(THUMBNAIL, userLinkResponse.getThumbnail());
        assertEquals(BOOKMARK_COUNT, userLinkResponse.getBookMarkCount());
        assertEquals(IS_READ, userLinkResponse.getIsRead());
        assertEquals(IS_MARK, userLinkResponse.getIsMark());
        assertEquals(tagResponseList, userLinkResponse.getTagList());
        assertEquals(CREATED_AT, userLinkResponse.getLinkCreatedTime());
        assertEquals(UPDATED_AT, userLinkResponse.getLinkUpdatedTime());
    }

    @DisplayName("UserLinkResponse Created Method - DTO")
    @Test
    void testCreate() {
        setUpLinkResponse();

        userLinkResponse = UserLinkResponse.create(linkResponse, IS_READ, IS_MARK, tagResponseList);

        assertEquals(LINK_ID, userLinkResponse.getLinkId());
        assertEquals(URL, userLinkResponse.getUrl());
        assertEquals(TITLE, userLinkResponse.getTitle());
        assertEquals(DESCRIPTION, userLinkResponse.getDescription());
        assertEquals(THUMBNAIL, userLinkResponse.getThumbnail());
        assertEquals(BOOKMARK_COUNT, userLinkResponse.getBookMarkCount());
        assertEquals(IS_READ, userLinkResponse.getIsRead());
        assertEquals(IS_MARK, userLinkResponse.getIsMark());
        assertEquals(tagResponseList, userLinkResponse.getTagList());
        assertEquals(CREATED_AT, userLinkResponse.getLinkCreatedTime());
        assertEquals(UPDATED_AT, userLinkResponse.getLinkUpdatedTime());
    }

}