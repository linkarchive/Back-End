package project.linkarchive.backend.hashtag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.linkarchive.backend.hashtag.domain.HashTag;

import java.util.List;
import java.util.Optional;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {

    Optional<HashTag> findByTag(String tag);

    @Query("SELECT h FROM HashTag h " +
            "WHERE h.id between 1L and 10L")
    List<HashTag> getArchiveTagList();

}