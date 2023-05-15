package project.linkarchive.backend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.linkarchive.backend.user.domain.UserHashTag;

import java.util.List;
import java.util.Optional;

public interface UserHashTagRepository extends JpaRepository<UserHashTag, Long> {

    Optional<UserHashTag> findByHashTagId(Long hashTagId);

    @Query("SELECT ut FROM UserHashTag ut " +
            "WHERE ut.user.id=:userId " +
            "ORDER BY ut.usageCount DESC ")
    List<UserHashTag> getByUserId(Long userId);

}