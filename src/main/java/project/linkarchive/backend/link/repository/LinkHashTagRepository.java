package project.linkarchive.backend.link.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.linkarchive.backend.link.domain.LinkHashTag;

import java.util.List;

public interface LinkHashTagRepository extends JpaRepository<LinkHashTag, Long> {

    List<LinkHashTag> findByLinkId(Long linkId);

    List<LinkHashTag> findByHashTagId(Long HashTagId);

}