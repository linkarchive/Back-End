package project.linkarchive.backend.user.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import static org.junit.jupiter.api.Assertions.*;
import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.NOT_FOUND_USER;
import static project.linkarchive.backend.util.constant.Constants.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest extends SetUpMockData {

    @Autowired
    protected UserRepository userRepository;

    @BeforeEach
    void repositorySetup() {
        setUpUser();
    }

    @DisplayName("유저 Repository - findBySocialId")
    @Test
    void testFindBySocialId() {
        userRepository.save(user);

        user = userRepository.findBySocialId(SOCIAL_ID)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));

        assertNotNull(user.getId());
        assertEquals(SOCIAL_ID, user.getSocialId());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(EMPTY, user.getNickname());
        assertEquals(EMPTY, user.getIntroduce());
    }

    @DisplayName("유저 Repository - findBySocialId NotFound")
    @Test
    void testFindBySocialIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            userRepository.findBySocialId(NONE_SOCIAL_ID)
                    .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
        });
    }

    @DisplayName("유저 Repository - existsUserByNickname")
    @Test
    void testExistsUserByNickname() {
        userRepository.save(user);

        Boolean exist = userRepository.existsUserByNickname(user.getNickname());

        Assertions.assertTrue(exist, user.getNickname());
    }

    @DisplayName("유저 Repository - existsUserByNickname NotFound")
    @Test
    void testExistsUserByNicknameNotFound() {
        Boolean exist = userRepository.existsUserByNickname(NONE_NICKNAME);

        Assertions.assertFalse(exist, EMPTY);
    }

    @DisplayName("유저 Repository - findByNickname")
    @Test
    void testFindByNickname() {
        userRepository.save(user);

        user = userRepository.findByNickname(EMPTY)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));

        assertNotNull(user.getId());
        assertEquals(SOCIAL_ID, user.getSocialId());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(EMPTY, user.getNickname());
        assertEquals(EMPTY, user.getIntroduce());
    }

    @DisplayName("유저 Repository - findByNickname NotFound")
    @Test
    void testFindByNicknameNotFound() {
        assertThrows(NotFoundException.class, () -> {
            userRepository.findByNickname(NONE_NICKNAME)
                    .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
        });
    }

}