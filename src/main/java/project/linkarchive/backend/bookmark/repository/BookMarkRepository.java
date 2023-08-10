package project.linkarchive.backend.bookmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.linkarchive.backend.bookmark.domain.Bookmark;

import java.util.List;
import java.util.Optional;

public interface BookMarkRepository extends JpaRepository<Bookmark, Long> {

    Boolean existsByLinkIdAndUserId(Long linkId, Long userId);

    Optional<Bookmark> findByLinkIdAndUserId(Long linkId, Long userId);

    List<Bookmark> findByLinkId(Long linkId);

    List<Bookmark> findByUserId(Long userId);

}