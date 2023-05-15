package project.linkarchive.backend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.linkarchive.backend.user.domain.UserHashTag;

import java.util.Optional;

public interface UserHashTagRepository extends JpaRepository<UserHashTag, Long> {
    Optional<UserHashTag> findByHashTagId(Long hashTagId);
}