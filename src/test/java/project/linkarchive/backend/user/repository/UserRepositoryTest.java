package project.linkarchive.backend.user.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.util.RepositoryTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.NOT_FOUND_USER;

class UserRepositoryTest extends RepositoryTest {

    @DisplayName("User Repository - findBySocialId")
    @Test
    void testFindBySocialId() {
        userRepository.save(user);

        User findUser = userRepository.findBySocialId(user.getSocialId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));

        assertAll(
                () -> assertThat(findUser.getId()).isEqualTo(user.getId()),
                () -> assertThat(findUser.getSocialId()).isEqualTo(user.getSocialId()),
                () -> assertThat(findUser.getNickname()).isEqualTo(user.getNickname()),
                () -> assertThat(findUser.getEmail()).isEqualTo(user.getEmail()),
                () -> assertThat(findUser.getIntroduce()).isEqualTo(user.getIntroduce()),
                () -> assertThat(findUser).isEqualTo(user)
        );
    }

    @DisplayName("User Repository - existsUserByNickname")
    @Test
    void testExistsUserByNickname() {
        userRepository.save(user);

        Boolean exist = userRepository.existsUserByNickname(user.getNickname());

        Assertions.assertTrue(exist, user.getNickname());
    }

    @DisplayName("User Repository - findByNickname")
    @Test
    void testFindByNickname() {
        userRepository.save(user);

        User findUser = userRepository.findByNickname(user.getNickname())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));


        assertAll(
                () -> assertThat(findUser.getId()).isEqualTo(user.getId()),
                () -> assertThat(findUser.getSocialId()).isEqualTo(user.getSocialId()),
                () -> assertThat(findUser.getNickname()).isEqualTo(user.getNickname()),
                () -> assertThat(findUser.getEmail()).isEqualTo(user.getEmail()),
                () -> assertThat(findUser.getIntroduce()).isEqualTo(user.getIntroduce()),
                () -> assertThat(findUser).isEqualTo(user)
        );
    }

}