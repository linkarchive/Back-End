package project.linkarchive.backend.url.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.linkarchive.backend.url.domain.Url;

public interface UrlRepository extends JpaRepository<Url, Long> {
}