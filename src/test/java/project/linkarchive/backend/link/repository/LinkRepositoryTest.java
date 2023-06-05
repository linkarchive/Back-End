package project.linkarchive.backend.link.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.link.domain.Link;
import project.linkarchive.backend.util.repository.LinkSetUpRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.NOT_FOUND_LINK;
import static project.linkarchive.backend.util.constant.Constants.MODIFY_LONG_VAL;

class LinkRepositoryTest extends LinkSetUpRepository {

    @DisplayName("링크 Repository - increaseBookMarkCount")
    @Test
    void increaseBookMarkCount() {
        linkRepository.increaseBookMarkCount(link.getId());
        entityManagerSetUp();

        Link getLink = linkRepository.findById(link.getId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_LINK));

        assertNotNull(getLink);
        assertEquals(link.getBookMarkCount() + MODIFY_LONG_VAL, getLink.getBookMarkCount());
    }

    @DisplayName("링크 Repository - decreaseBookMarkCount")
    @Test
    void decreaseBookMarkCount() {
        linkRepository.decreaseBookMarkCount(link.getId());
        entityManagerSetUp();

        Link getLink = linkRepository.findById(link.getId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_LINK));

        assertNotNull(getLink);
        assertEquals(link.getBookMarkCount() - MODIFY_LONG_VAL, getLink.getBookMarkCount());
    }

}