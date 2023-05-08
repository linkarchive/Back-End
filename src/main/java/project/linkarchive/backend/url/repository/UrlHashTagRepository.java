package project.linkarchive.backend.url.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.linkarchive.backend.url.domain.UrlHashTag;

import java.util.List;

public interface UrlHashTagRepository extends JpaRepository<UrlHashTag, Long> {

    List<UrlHashTag> findByUrlId(Long urlId);

}