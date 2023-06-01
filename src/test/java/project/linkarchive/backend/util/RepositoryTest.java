package project.linkarchive.backend.util;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.user.repository.UserRepository;
import project.linkarchive.backend.util.constant.SetUpData;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class RepositoryTest extends SetUpData {

    @Autowired
    protected UserRepository userRepository;

    @AfterEach
    void cleanUp() {
        userRepository.delete(user);
    }

}