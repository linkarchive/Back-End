package project.linkarchive.backend.hashtag.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.advice.exception.ExceptionCodeConst;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.hashtag.domain.UserHashTag;
import project.linkarchive.backend.util.repository.UserHashTagSetUpRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.NOT_FOUND_HASHTAG;
import static project.linkarchive.backend.util.constant.Constants.EMPTY_LONG_VAL;
import static project.linkarchive.backend.util.constant.Constants.MODIFY_LONG_VAL;

class UserHashTagRepositoryTest extends UserHashTagSetUpRepository {

    @DisplayName("유저 해시태그 Repository - findByHashTagId")
    @Test
    void testFindByHashTagId() {
        UserHashTag getUserHashTag = userHashTagRepository.findByHashTagId(hashTag.getId())
                .orElseThrow(() -> new NotFoundException(ExceptionCodeConst.NOT_FOUND_HASHTAG));

        assertAll(
                () -> assertNotNull(getUserHashTag),
                () -> assertThat(getUserHashTag.getId()).isEqualTo(userHashTag.getId()),
                () -> assertThat(getUserHashTag.getUsageCount()).isEqualTo(userHashTag.getUsageCount()),
                () -> assertThat(getUserHashTag.getUser()).isEqualTo(userHashTag.getUser()),
                () -> assertThat(getUserHashTag.getUser().getId()).isEqualTo(userHashTag.getUser().getId()),
                () -> assertThat(getUserHashTag.getUser().getSocialId()).isEqualTo(userHashTag.getUser().getSocialId()),
                () -> assertThat(getUserHashTag.getUser().getNickname()).isEqualTo(userHashTag.getUser().getNickname()),
                () -> assertThat(getUserHashTag.getUser().getEmail()).isEqualTo(userHashTag.getUser().getEmail()),
                () -> assertThat(getUserHashTag.getUser().getIntroduce()).isEqualTo(userHashTag.getUser().getIntroduce()),
                () -> assertThat(getUserHashTag.getHashTag()).isEqualTo(userHashTag.getHashTag()),
                () -> assertThat(getUserHashTag.getHashTag().getId()).isEqualTo(userHashTag.getHashTag().getId()),
                () -> assertThat(getUserHashTag.getHashTag().getTag()).isEqualTo(userHashTag.getHashTag().getTag())
        );
    }

    @DisplayName("유저 해시태그 Repository - findByHashTagId NotFound")
    @Test
    void testFindByHashTagIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            userHashTagRepository.findByHashTagId(EMPTY_LONG_VAL)
                    .orElseThrow(() -> new NotFoundException(NOT_FOUND_HASHTAG));
        });
    }

    @DisplayName("유저 해시태그 Repository - increaseUsageCount")
    @Test
    void testIncreaseUsageCount() {
        userHashTagRepository.increaseUsageCount(hashTag.getId());
        entityManagerSetUp();

        UserHashTag getUserHashTag = userHashTagRepository.findById(hashTag.getId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_HASHTAG));

        assertNotNull(getUserHashTag);
        assertEquals(userHashTag.getUsageCount() + MODIFY_LONG_VAL, getUserHashTag.getUsageCount());
    }

}