package project.linkarchive.backend.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.linkarchive.backend.auth.domain.RefreshToken;
import project.linkarchive.backend.user.domain.User;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Boolean existsByUserIdAndAgent(Long userId, String agent);

    Optional<RefreshToken> findByUserId(Long userId);

}