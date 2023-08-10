package project.linkarchive.backend.link.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.linkarchive.backend.link.domain.LinkHashtag;

import java.util.List;

public interface LinkHashTagRepository extends JpaRepository<LinkHashtag, Long> {

    List<LinkHashtag> findByLinkId(Long linkId);

}