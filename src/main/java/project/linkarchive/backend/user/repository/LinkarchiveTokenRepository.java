package project.linkarchive.backend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.linkarchive.backend.user.domain.LinkarchiveToken;

import java.util.Optional;

public interface LinkarchiveTokenRepository extends JpaRepository<LinkarchiveToken, Long> {
    Optional<LinkarchiveToken> findByUserId(Long userId);
}
