package project.linkarchive.backend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.linkarchive.backend.user.domain.UserProfileImage;

public interface UserProfileImageRepository extends JpaRepository<UserProfileImage, Long> {//
}
