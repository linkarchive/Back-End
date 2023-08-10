package project.linkarchive.backend.hashtag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.linkarchive.backend.hashtag.domain.UserHashtag;

import java.util.Optional;

public interface UserHashTagRepository extends JpaRepository<UserHashtag, Long> {

    Optional<UserHashtag> findByHashtagId(Long hashtagId);

    @Modifying
    @Query("UPDATE UserHashtag uh SET uh.usageCount = uh.usageCount + 1 WHERE uh.id = :hashtagId")
    void increaseUsageCount(@Param("hashtagId") Long hashtagId);

}