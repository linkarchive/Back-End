package project.linkarchive.backend.link.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.link.enums.LinkStatus;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static project.linkarchive.backend.link.enums.LinkStatus.ACTIVE;
import static project.linkarchive.backend.util.constant.Constants.*;

class LinkTest extends SetUpMockData {

    @BeforeEach
    void setUp() {
        setUpLink();
    }

    @DisplayName("Link getLinkId- Domain")
    @Test
    void testGetLinkId() {
        assertEquals(LINK_ID, link.getId());
    }

    @DisplayName("Link getUrl - Domain")
    @Test
    void testGetUrl() {
        assertEquals(URL, link.getUrl());
    }

    @DisplayName("Link getTitle - Domain")
    @Test
    void getTitle() {
        assertEquals(TITLE, link.getTitle());
    }

    @DisplayName("Link getDescription - Domain")
    @Test
    void getDescription() {
        assertEquals(DESCRIPTION, link.getDescription());
    }

    @DisplayName("Link getThumbnail - Domain")
    @Test
    void getThumbnail() {
        assertEquals(THUMBNAIL, link.getThumbnail());
    }

    @DisplayName("Link getBookMarkCount - Domain")
    @Test
    void getBookMarkCount() {
        assertEquals(BOOKMARK_COUNT, link.getBookMarkCount());
    }

    @DisplayName("Link getLinkStatus - Domain")
    @Test
    void getLinkStatus() {
        assertEquals(LINK_STATUS_ACTIVE, link.getLinkStatus());
    }

    @DisplayName("Link getUser - Domain")
    @Test
    void getUser() {
        assertEquals(user, link.getUser());
    }

    @DisplayName("Link Constructor - Domain")
    @Test
    void testLinkConstructor() {
        link = new Link(LINK_ID, URL, TITLE, DESCRIPTION, THUMBNAIL, BOOKMARK_COUNT, ACTIVE, user);

        assertEquals(LINK_ID, link.getId());
        assertEquals(URL, link.getUrl());
        assertEquals(TITLE, link.getTitle());
        assertEquals(DESCRIPTION, link.getDescription());
        assertEquals(THUMBNAIL, link.getThumbnail());
        assertEquals(BOOKMARK_COUNT, link.getBookMarkCount());
        assertEquals(LINK_STATUS_ACTIVE, link.getLinkStatus());
        assertEquals(user, link.getUser());
    }

    @DisplayName("Link Builder Pattern - Domain")
    @Test
    void builder() {
        link = Link.builder()
                .url(URL)
                .title(TITLE)
                .description(DESCRIPTION)
                .thumbnail(THUMBNAIL)
                .bookMarkCount(BOOKMARK_COUNT)
                .linkStatus(ACTIVE)
                .user(user)
                .build();

        assertEquals(URL, link.getUrl());
        assertEquals(TITLE, link.getTitle());
        assertEquals(DESCRIPTION, link.getDescription());
        assertEquals(THUMBNAIL, link.getThumbnail());
        assertEquals(BOOKMARK_COUNT, link.getBookMarkCount());
        assertEquals(LINK_STATUS_ACTIVE, link.getLinkStatus());
        assertEquals(user, link.getUser());
    }

    @DisplayName("Link create method - Domain")
    @Test
    void build() {
        setUpTagList();
        setUpCreateLinkRequest();

        link = Link.create(createLinkRequest, user);

        assertEquals(URL, link.getUrl());
        assertEquals(TITLE, link.getTitle());
        assertEquals(DESCRIPTION, link.getDescription());
        assertEquals(THUMBNAIL, link.getThumbnail());
        assertEquals(BOOKMARK_COUNT, link.getBookMarkCount());
        assertEquals(LINK_STATUS_ACTIVE, link.getLinkStatus());
        assertEquals(user, link.getUser());
    }

    @DisplayName("Link delete - Domain")
    @Test
    void testDelete() {
        LinkStatus oldLinkStatus = link.getLinkStatus();

        link.delete();
        LinkStatus newLinkStatus = link.getLinkStatus();

        assertNotEquals(oldLinkStatus, newLinkStatus);
        assertEquals(LINK_STATUS_TRASH, newLinkStatus);
    }

}