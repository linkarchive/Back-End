package project.linkarchive.backend.bookmark.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.link.domain.Link;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.util.setUpData.BookMarkSetUpData;

import static org.junit.jupiter.api.Assertions.*;

class BookMarkTest extends BookMarkSetUpData {

    @DisplayName("북마크 getUser - Domain")
    @Test
    void testGetUser() {
        User getUser = bookMark.getUser();

        assertNotNull(getUser);
        assertEquals(user, getUser);
    }

    @DisplayName("북마크 getLink - Domain")
    @Test
    void testGetLink() {
        Link getLink = bookMark.getLink();

        assertNotNull(getLink);
        assertEquals(link, getLink);
    }

    @DisplayName("북마크 Builder 패턴 - Domain")
    @Test
    void testBuilder() {
        BookMark getBookMark = BookMark.builder()
                .user(user)
                .link(link)
                .build();

        assertNotNull(getBookMark);
        assertEquals(user, getBookMark.getUser());
        assertEquals(link, getBookMark.getLink());
    }

    @DisplayName("북마크 Build 메서드 - Domain")
    @Test
    void testBuild() {
        BookMark getBookMark = BookMark.build(user, link);

        assertNotNull(getBookMark);
        assertEquals(user, getBookMark.getUser());
        assertEquals(link, getBookMark.getLink());
    }
}