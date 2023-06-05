package project.linkarchive.backend.user.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.util.repository.UserSetUpRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.NOT_FOUND_USER;
import static project.linkarchive.backend.util.constant.Constants.EMPTY;

class UserRepositoryTest extends UserSetUpRepository {

    @DisplayName("유저 Repository - findBySocialId")
    @Test
    void testFindBySocialId() {
        User findUser = userRepository.findBySocialId(user.getSocialId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));

        assertAll(
                () -> assertNotNull(findUser),
                () -> assertThat(findUser.getId()).isEqualTo(user.getId()),
                () -> assertThat(findUser.getSocialId()).isEqualTo(user.getSocialId()),
                () -> assertThat(findUser.getNickname()).isEqualTo(user.getNickname()),
                () -> assertThat(findUser.getEmail()).isEqualTo(user.getEmail()),
                () -> assertThat(findUser.getIntroduce()).isEqualTo(user.getIntroduce()),
                () -> assertThat(findUser).isEqualTo(user)
        );
    }

    @DisplayName("유저 Repository - findBySocialId NotFound")
    @Test
    void testFindBySocialIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            userRepository.findBySocialId(EMPTY)
                    .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
        });
    }

    @DisplayName("유저 Repository - existsUserByNickname")
    @Test
    void testExistsUserByNickname() {
        Boolean exist = userRepository.existsUserByNickname(user.getNickname());

        Assertions.assertTrue(exist, user.getNickname());
    }

    @DisplayName("유저 Repository - existsUserByNickname NotFound")
    @Test
    void testExistsUserByNicknameNotFound() {
        Boolean exist = userRepository.existsUserByNickname(EMPTY);

        Assertions.assertFalse(exist, EMPTY);
    }

    @DisplayName("유저 Repository - findByNickname")
    @Test
    void testFindByNickname() {
        User findUser = userRepository.findByNickname(user.getNickname())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));

        assertAll(
                () -> assertNotNull(findUser),
                () -> assertThat(findUser.getId()).isEqualTo(user.getId()),
                () -> assertThat(findUser.getSocialId()).isEqualTo(user.getSocialId()),
                () -> assertThat(findUser.getNickname()).isEqualTo(user.getNickname()),
                () -> assertThat(findUser.getEmail()).isEqualTo(user.getEmail()),
                () -> assertThat(findUser.getIntroduce()).isEqualTo(user.getIntroduce()),
                () -> assertThat(findUser).isEqualTo(user)
        );
    }

    @DisplayName("유저 Repository - findByNickname NotFound")
    @Test
    void testFindByNicknameNotFound() {
        assertThrows(NotFoundException.class, () -> {
            userRepository.findByNickname(EMPTY)
                    .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
        });
    }

}