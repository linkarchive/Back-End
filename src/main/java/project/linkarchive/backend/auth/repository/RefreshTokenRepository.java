package project.linkarchive.backend.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.linkarchive.backend.auth.domain.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Boolean existsByUserIdAndAgent(Long userId, String agent);

}