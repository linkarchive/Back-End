package project.linkarchive.backend.bookmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.linkarchive.backend.bookmark.domain.BookMark;

import java.util.Optional;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

    Boolean existsByLinkId(Long linkId);

    Optional<BookMark> findByLinkIdAndUserId(Long linkId, Long userId);

}