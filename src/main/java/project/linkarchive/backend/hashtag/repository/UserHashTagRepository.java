package project.linkarchive.backend.hashtag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.linkarchive.backend.hashtag.domain.UserHashTag;

import java.util.Optional;

public interface UserHashTagRepository extends JpaRepository<UserHashTag, Long> {

    Optional<UserHashTag> findByHashTagId(Long hashTagId);

    @Modifying
    @Query("UPDATE UserHashTag uh SET uh.usageCount = uh.usageCount + 1 WHERE uh.id = :hashtagId")
    void increaseUsageCount(@Param("hashtagId") Long hashtagId);

}