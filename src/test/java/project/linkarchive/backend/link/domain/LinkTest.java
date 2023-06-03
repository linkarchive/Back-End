package project.linkarchive.backend.link.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.util.setUpData.LinkSetUpData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static project.linkarchive.backend.util.constant.Constants.*;

class LinkTest extends LinkSetUpData {

    @DisplayName("Link getUrl - Domain")
    @Test
    void testGetUrl() {
        String url = link.getUrl();

        assertNotNull(url);
        assertEquals(URL, url);
    }

    @DisplayName("Link getTitle - Domain")
    @Test
    void getTitle() {
        String title = link.getTitle();

        assertNotNull(title);
        assertEquals(TITLE, title);
    }

    @DisplayName("Link getDescription - Domain")
    @Test
    void getDescription() {
        String description = link.getDescription();

        assertNotNull(description);
        assertEquals(DESCRIPTION, description);
    }

    @DisplayName("Link getThumbnail - Domain")
    @Test
    void getThumbnail() {
        String thumbnail = link.getThumbnail();

        assertNotNull(thumbnail);
        assertEquals(THUMBNAIL, thumbnail);
    }

    @DisplayName("Link getBookMarkCount - Domain")
    @Test
    void getBookMarkCount() {
        Long bookMarkCount = link.getBookMarkCount();

        assertNotNull(bookMarkCount);
        assertEquals(BOOKMARK_COUNT, bookMarkCount);
    }

    @DisplayName("Link getUser - Domain")
    @Test
    void getUser() {
        User getUser = link.getUser();

        assertNotNull(getUser);
        assertEquals(user, getUser);
    }

    @DisplayName("Link Builder 패턴 - Domain")
    @Test
    void builder() {
        Link getLink = Link.builder()
                .url(URL)
                .title(TITLE)
                .description(DESCRIPTION)
                .thumbnail(THUMBNAIL)
                .bookMarkCount(BOOKMARK_COUNT)
                .user(user)
                .build();

        assertNotNull(getLink);
        assertEquals(URL, getLink.getUrl());
        assertEquals(TITLE, getLink.getTitle());
        assertEquals(DESCRIPTION, getLink.getDescription());
        assertEquals(THUMBNAIL, getLink.getThumbnail());
        assertEquals(BOOKMARK_COUNT, getLink.getBookMarkCount());
        assertEquals(user, getLink.getUser());
    }

    @DisplayName("Link Build 메서드 - Domain")
    @Test
    void build() {
        Link getLink = Link.build(createLinkRequest, user);

        assertNotNull(getLink);
        assertEquals(URL, getLink.getUrl());
        assertEquals(TITLE, getLink.getTitle());
        assertEquals(DESCRIPTION, getLink.getDescription());
        assertEquals(THUMBNAIL, getLink.getThumbnail());
        assertEquals(EMPTY_COUNT, getLink.getBookMarkCount());
        assertEquals(user, getLink.getUser());
    }

}