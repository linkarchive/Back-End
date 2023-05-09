package project.linkarchive.backend.bookmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.linkarchive.backend.bookmark.domain.BookMark;

import java.util.Optional;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

    Boolean existsByUrlId(Long urlId);

    Optional<BookMark> findByUrlId(Long urlId);

}