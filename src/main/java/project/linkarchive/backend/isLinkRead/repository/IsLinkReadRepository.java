package project.linkarchive.backend.isLinkRead.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.linkarchive.backend.isLinkRead.domain.IsLinkRead;

public interface IsLinkReadRepository extends JpaRepository<IsLinkRead, Long> {

    Boolean existsByLinkIdAndUserId(Long linkId, Long userId);

}