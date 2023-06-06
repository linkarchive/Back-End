package project.linkarchive.backend.bookmark.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.bookmark.domain.BookMark;
import project.linkarchive.backend.util.repository.BookMarkSetUpRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.NOT_FOUND_BOOKMARK;
import static project.linkarchive.backend.util.constant.Constants.EMPTY_LONG_VAL;

class BookMarkRepositoryTest extends BookMarkSetUpRepository {

    @DisplayName("북마크 Repository - existsByLinkIdAndUserId")
    @Test
    void testExistsByLinkIdAndUserId() {
        Boolean exist = bookMarkRepository.existsByLinkIdAndUserId(link.getId(), user.getId());

        assertTrue(exist);
    }

    @DisplayName("북마크 Repository - existsByLinkIdAndUserId NotFound")
    @Test
    void testExistsByLinkIdAndUserIdNotFound() {
        Boolean exist = bookMarkRepository.existsByLinkIdAndUserId(EMPTY_LONG_VAL, EMPTY_LONG_VAL);

        assertFalse(exist);
    }

    @DisplayName("북마크 Repository - findByLinkIdAndUserId")
    @Test
    void testFindByLinkIdAndUserId() {
        BookMark getBookMark = bookMarkRepository.findByLinkIdAndUserId(link.getId(), user.getId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_BOOKMARK));

        assertAll(
                () -> assertNotNull(getBookMark),
                () -> assertThat(getBookMark.getId()).isEqualTo(bookMark.getId()),
                () -> assertThat(getBookMark.getUser()).isEqualTo(bookMark.getUser()),
                () -> assertThat(getBookMark.getUser().getId()).isEqualTo(bookMark.getUser().getId()),
                () -> assertThat(getBookMark.getUser().getSocialId()).isEqualTo(bookMark.getUser().getSocialId()),
                () -> assertThat(getBookMark.getUser().getNickname()).isEqualTo(bookMark.getUser().getNickname()),
                () -> assertThat(getBookMark.getUser().getEmail()).isEqualTo(bookMark.getUser().getEmail()),
                () -> assertThat(getBookMark.getUser().getIntroduce()).isEqualTo(bookMark.getUser().getIntroduce()),
                () -> assertThat(getBookMark.getLink().getId()).isEqualTo(bookMark.getLink().getId()),
                () -> assertThat(getBookMark.getLink().getUrl()).isEqualTo(bookMark.getLink().getUrl()),
                () -> assertThat(getBookMark.getLink().getTitle()).isEqualTo(bookMark.getLink().getTitle()),
                () -> assertThat(getBookMark.getLink().getDescription()).isEqualTo(bookMark.getLink().getDescription()),
                () -> assertThat(getBookMark.getLink().getThumbnail()).isEqualTo(bookMark.getLink().getThumbnail()),
                () -> assertThat(getBookMark.getLink().getBookMarkCount()).isEqualTo(bookMark.getLink().getBookMarkCount())
        );
    }

    @DisplayName("북마크 Repository - findByLinkIdAndUserId NotFound")
    @Test
    void testFindByLinkIdAndUserIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            bookMarkRepository.findByLinkIdAndUserId(EMPTY_LONG_VAL, EMPTY_LONG_VAL)
                    .orElseThrow(() -> new NotFoundException(NOT_FOUND_BOOKMARK));
        });
    }

    @DisplayName("북마크 Repository - findByUserId")
    @Test
    void testFindByUserId() {
        List<BookMark> bookmarkList = bookMarkRepository.findByUserId(user.getId());

        assertThat(bookmarkList).isNotEmpty();
        assertThat(bookmarkList).extracting(BookMark::getUser).containsOnly(user);
    }

    @DisplayName("북마크 Repository - findByUserId NotFound")
    @Test
    void testFindByUserIdNotFound() {
        List<BookMark> bookmarkList = bookMarkRepository.findByUserId(EMPTY_LONG_VAL);

        assertThat(bookmarkList).isEmpty();
    }

}
