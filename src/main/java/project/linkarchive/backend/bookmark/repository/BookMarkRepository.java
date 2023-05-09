package project.linkarchive.backend.bookmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.linkarchive.backend.bookmark.domain.BookMark;
import project.linkarchive.backend.url.domain.Url;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

    Boolean existsByUrl(Url url);

}