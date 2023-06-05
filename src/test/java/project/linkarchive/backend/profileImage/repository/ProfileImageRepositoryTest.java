package project.linkarchive.backend.profileImage.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.profileImage.domain.ProfileImage;
import project.linkarchive.backend.util.repository.ProfileImageSetUpRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.NOT_FOUND_PROFILE_IMAGE;
import static project.linkarchive.backend.util.constant.Constants.EMPTY_LONG_VAL;

class ProfileImageRepositoryTest extends ProfileImageSetUpRepository {

    @DisplayName("프로필 이미지 Repository - findByUserId")
    @Test
    void testFindByUserId() {
        ProfileImage getProfileImage = profileImageRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_PROFILE_IMAGE));

        Assertions.assertAll(
                () -> assertNotNull(getProfileImage),
                () -> assertThat(getProfileImage.getId()).isEqualTo(profileImage.getId()),
                () -> assertThat(getProfileImage.getProfileImageFilename()).isEqualTo(profileImage.getProfileImageFilename()),
                () -> assertThat(getProfileImage.getUser()).isEqualTo(profileImage.getUser()),
                () -> assertThat(getProfileImage.getUser().getId()).isEqualTo(profileImage.getUser().getId()),
                () -> assertThat(getProfileImage.getUser().getSocialId()).isEqualTo(profileImage.getUser().getSocialId()),
                () -> assertThat(getProfileImage.getUser().getNickname()).isEqualTo(profileImage.getUser().getNickname()),
                () -> assertThat(getProfileImage.getUser().getEmail()).isEqualTo(profileImage.getUser().getEmail()),
                () -> assertThat(getProfileImage.getUser().getIntroduce()).isEqualTo(profileImage.getUser().getIntroduce())
        );
    }

    @DisplayName("프로필 이미지 Repository - findByUserId NotFound")
    @Test
    void testFindByUserIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            profileImageRepository.findByUserId(EMPTY_LONG_VAL)
                    .orElseThrow(() -> new NotFoundException(NOT_FOUND_PROFILE_IMAGE));
        });
    }

}