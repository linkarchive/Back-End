package project.linkarchive.backend.link.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.linkarchive.backend.link.domain.UrlHashTag;

import java.util.List;

public interface UrlHashTagRepository extends JpaRepository<UrlHashTag, Long> {

    List<UrlHashTag> findByLinkId(Long linkId);

}