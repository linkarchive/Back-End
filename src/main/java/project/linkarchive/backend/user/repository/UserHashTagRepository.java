package project.linkarchive.backend.user.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.linkarchive.backend.user.domain.UserHashTag;

import java.util.List;
import java.util.Optional;

public interface UserHashTagRepository extends JpaRepository<UserHashTag, Long> {

    Optional<UserHashTag> findByHashTagId(Long hashTagId);

    @Query("SELECT u FROM UserHashTag u WHERE u.user.id = :userId ORDER BY u.usageCount DESC")
    List<UserHashTag> findByUserId(@Param("userId") Long userId, PageRequest limit);

}