package project.linkarchive.backend.link.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.linkarchive.backend.link.domain.Link;
import project.linkarchive.backend.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {

    @Modifying
    @Query("UPDATE Link l SET l.bookmarkCount = l.bookmarkCount + 1 WHERE l.id = :linkId")
    void increaseBookmarkCount(@Param("linkId") Long linkId);

    @Modifying
    @Query("UPDATE Link l SET l.bookmarkCount = l.bookmarkCount - 1 WHERE l.id = :linkId")
    void decreaseBookmarkCount(@Param("linkId") Long linkId);

    @Modifying
    @Query("UPDATE Link l SET l.bookmarkCount = 0 WHERE l.id = :linkId")
    void resetBookmarkCount(@Param("linkId") Long linkId);

    Optional<Link> findByIdAndUserId(Long linkId, Long userId);

    @Query("SELECT l FROM Link l WHERE l.linkStatus = 'ACTIVE' AND l.user = :user")
    List<Link> findByUser(@Param("user") User user);

    @Query("SELECT l FROM Link l WHERE l.linkStatus = 'TRASH' AND l.updatedAt < :dateTime")
    List<Link> findOldLinksInTrashStatus(@Param("dateTime") LocalDateTime dateTime);

}