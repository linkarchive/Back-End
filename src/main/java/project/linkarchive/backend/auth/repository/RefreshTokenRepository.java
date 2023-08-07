package project.linkarchive.backend.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.linkarchive.backend.auth.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    Optional<RefreshToken> findByUserIdAndAgent(Long userId, String agent);

}