package project.linkarchive.backend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.linkarchive.backend.user.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findBySocialId(String socialId);

    Boolean existsUserByNickname(String nickname);

    Optional<User> findByNickname(String nickname);

    Optional<User> findById(Long userId);

}