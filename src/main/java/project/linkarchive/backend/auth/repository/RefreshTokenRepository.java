package project.linkarchive.backend.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.linkarchive.backend.auth.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByRefreshTokenAndAgent(String refreshToken, String agent);

    @Query("SELECT r FROM RefreshToken r " +
            "WHERE r.user.id =:userId and r.agent =:agent")
    Optional<RefreshToken> getRefreshTokenByUserIdAndAgent(Long userId, String agent);

}