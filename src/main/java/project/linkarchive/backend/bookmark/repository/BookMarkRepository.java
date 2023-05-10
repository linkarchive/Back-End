package project.linkarchive.backend.bookmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.linkarchive.backend.bookmark.domain.BookMark;

import java.util.Optional;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

    Boolean existsByUrlId(Long urlId);

    Optional<BookMark> findByUrlId(Long urlId);

    @Modifying
    @Query("UPDATE Url u SET u.bookMarkCount = u.bookMarkCount + 1 WHERE u.id = :urlId")
    void increaseBookMarkCount(@Param("urlId") Long urlId);

    @Modifying
    @Query("UPDATE Url u SET u.bookMarkCount = u.bookMarkCount - 1 WHERE u.id = :urlId")
    void decreaseBookMarkCount(@Param("urlId") Long urlId);

}