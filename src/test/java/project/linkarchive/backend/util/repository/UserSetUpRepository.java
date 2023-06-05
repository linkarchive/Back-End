package project.linkarchive.backend.util.repository;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import project.linkarchive.backend.user.repository.UserRepository;
import project.linkarchive.backend.util.setUpData.UserSetUpData;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserSetUpRepository extends UserSetUpData {

    @Autowired
    protected UserRepository userRepository;

    @BeforeEach
    void repositorySetup() {
        userRepository.save(user);
    }

}