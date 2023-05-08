package project.linkarchive.backend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.linkarchive.backend.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);

    public User findBySocialId(Long socialId);
}
