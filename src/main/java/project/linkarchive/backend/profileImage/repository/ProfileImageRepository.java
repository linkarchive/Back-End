package project.linkarchive.backend.profileImage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.linkarchive.backend.profileImage.domain.ProfileImage;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
}