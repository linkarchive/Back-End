package project.linkarchive.backend.hashtag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.linkarchive.backend.hashtag.domain.Hashtag;

import java.util.List;
import java.util.Optional;

public interface HashTagRepository extends JpaRepository<Hashtag, Long> {

    Optional<Hashtag> findByTag(String tag);

    @Query("SELECT h FROM Hashtag h " +
            "WHERE h.id between 1L and 10L")
    List<Hashtag> getArchiveTagList();

}