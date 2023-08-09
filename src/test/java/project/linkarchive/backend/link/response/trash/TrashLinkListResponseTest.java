package project.linkarchive.backend.link.response.trash;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static project.linkarchive.backend.util.constant.Constants.*;

class TrashLinkListResponseTest extends SetUpMockData {

    @BeforeEach
    void setUp() {
        setUpTrashLinkListResponse();
    }

    @DisplayName("TrashLinkListResponse Getter - DTO")
    @Test
    void testTrashLinkListResponse() {
        assertEquals(LINK_ID, trashLinkListResponse.getLinkId());
        assertEquals(URL, trashLinkListResponse.getUrl());
        assertEquals(TITLE, trashLinkListResponse.getTitle());
        assertEquals(DESCRIPTION, trashLinkListResponse.getDescription());
        assertEquals(THUMBNAIL, trashLinkListResponse.getThumbnail());
        assertEquals(BOOKMARK_COUNT, trashLinkListResponse.getBookMarkCount());
        assertEquals(tagResponseList, trashLinkListResponse.getTagList());
        assertEquals(CREATED_AT, trashLinkListResponse.getLinkCreatedTime());
    }

    @DisplayName("TrashLinkListResponse Constructor - DTO")
    @Test
    void testConstructor() {
        trashLinkListResponse = new TrashLinkListResponse(LINK_ID, URL, TITLE, DESCRIPTION, THUMBNAIL, BOOKMARK_COUNT, tagResponseList, CREATED_AT);

        assertEquals(LINK_ID, trashLinkListResponse.getLinkId());
        assertEquals(URL, trashLinkListResponse.getUrl());
        assertEquals(TITLE, trashLinkListResponse.getTitle());
        assertEquals(DESCRIPTION, trashLinkListResponse.getDescription());
        assertEquals(THUMBNAIL, trashLinkListResponse.getThumbnail());
        assertEquals(BOOKMARK_COUNT, trashLinkListResponse.getBookMarkCount());
        assertEquals(tagResponseList, trashLinkListResponse.getTagList());
        assertEquals(CREATED_AT, trashLinkListResponse.getLinkCreatedTime());
    }

    @DisplayName("TrashLinkListResponseTest Builder Pattern - DTO")
    @Test
    void testBuilder() {
        trashLinkListResponse = TrashLinkListResponse.builder()
                .linkId(LINK_ID)
                .url(URL)
                .title(TITLE)
                .description(DESCRIPTION)
                .thumbnail(THUMBNAIL)
                .bookMarkCount(BOOKMARK_COUNT)
                .tagList(tagResponseList)
                .linkCreatedTime(CREATED_AT)
                .build();

        assertEquals(LINK_ID, trashLinkListResponse.getLinkId());
        assertEquals(URL, trashLinkListResponse.getUrl());
        assertEquals(TITLE, trashLinkListResponse.getTitle());
        assertEquals(DESCRIPTION, trashLinkListResponse.getDescription());
        assertEquals(THUMBNAIL, trashLinkListResponse.getThumbnail());
        assertEquals(BOOKMARK_COUNT, trashLinkListResponse.getBookMarkCount());
        assertEquals(tagResponseList, trashLinkListResponse.getTagList());
        assertEquals(CREATED_AT, trashLinkListResponse.getLinkCreatedTime());
    }

    @DisplayName("TrashLinkListResponseTest Create Method - DTO")
    @Test
    void testCreate() {
        setUpTrashLinkResponse();

        trashLinkListResponse = TrashLinkListResponse.create(trashLinkResponse, tagResponseList);

        assertEquals(LINK_ID, trashLinkListResponse.getLinkId());
        assertEquals(URL, trashLinkListResponse.getUrl());
        assertEquals(TITLE, trashLinkListResponse.getTitle());
        assertEquals(DESCRIPTION, trashLinkListResponse.getDescription());
        assertEquals(THUMBNAIL, trashLinkListResponse.getThumbnail());
        assertEquals(BOOKMARK_COUNT, trashLinkListResponse.getBookMarkCount());
        assertEquals(tagResponseList, trashLinkListResponse.getTagList());
        assertEquals(CREATED_AT, trashLinkListResponse.getLinkCreatedTime());
    }

}