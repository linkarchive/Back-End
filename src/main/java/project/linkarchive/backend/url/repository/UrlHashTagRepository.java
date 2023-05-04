package project.linkarchive.backend.url.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.linkarchive.backend.url.domain.UrlHashTag;

public interface UrlHashTagRepository extends JpaRepository<UrlHashTag, Long> {
}