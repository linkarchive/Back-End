package project.linkarchive.backend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.linkarchive.backend.user.domain.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Boolean existsByUserId(Long userId);

}