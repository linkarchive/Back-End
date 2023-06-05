package project.linkarchive.backend.profileImage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.linkarchive.backend.profileImage.domain.ProfileImage;

import java.util.Optional;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {

    Optional<ProfileImage> findByUserId(Long userId);

}