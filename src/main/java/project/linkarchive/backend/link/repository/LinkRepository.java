package project.linkarchive.backend.link.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.linkarchive.backend.link.domain.Link;

import java.util.List;
import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {

    @Modifying
    @Query("UPDATE Link l SET l.bookMarkCount = l.bookMarkCount + 1 WHERE l.id = :linkId")
    void increaseBookMarkCount(@Param("linkId") Long linkId);

    @Modifying
    @Query("UPDATE Link l SET l.bookMarkCount = l.bookMarkCount - 1 WHERE l.id = :linkId")
    void decreaseBookMarkCount(@Param("linkId") Long linkId);

    Optional<Link> findByIdAndUserId(Long linkId, Long userId);

    List<Link> findByUserId(Long userId);

}