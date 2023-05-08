package project.linkarchive.backend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.linkarchive.backend.user.domain.ProfileImage;

import java.util.Optional;

public interface UserProfileImageRepository extends JpaRepository<ProfileImage, Long> {//

    Optional<ProfileImage> findByUserId(Long userId);

}