package project.linkarchive.backend.hashtag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.linkarchive.backend.hashtag.domain.UserHashTag;

import java.util.Optional;

public interface UserHashTagRepository extends JpaRepository<UserHashTag, Long> {

    Optional<UserHashTag> findByHashTagId(Long hashTagId);

}