package project.linkarchive.backend.bookmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.linkarchive.backend.bookmark.domain.BookMark;

import java.util.List;
import java.util.Optional;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

    Boolean existsByLinkIdAndUserId(Long linkId, Long userId);

    Optional<BookMark> findByLinkIdAndUserId(Long linkId, Long userId);

    List<BookMark> findByLinkId(Long linkId);

    List<BookMark> findByUserId(Long userId);

}