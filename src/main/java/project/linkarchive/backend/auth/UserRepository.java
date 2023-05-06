package project.linkarchive.backend.auth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public UserEntity findByKakaoEmail(String kakaoEmail);

    public UserEntity findByUserCode(String userCode);
}
