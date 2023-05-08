package project.linkarchive.backend.hashtag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.linkarchive.backend.hashtag.domain.HashTag;

import java.util.Optional;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {

    Optional<HashTag> findByTag(String tag);

}