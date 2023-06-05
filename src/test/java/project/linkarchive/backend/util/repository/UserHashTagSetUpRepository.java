package project.linkarchive.backend.util.repository;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import project.linkarchive.backend.hashtag.repository.HashTagRepository;
import project.linkarchive.backend.hashtag.repository.UserHashTagRepository;
import project.linkarchive.backend.user.repository.UserRepository;
import project.linkarchive.backend.util.setUpData.UserHashTagSetUpData;

import javax.persistence.EntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserHashTagSetUpRepository extends UserHashTagSetUpData {

    @Autowired
    protected EntityManager entityManager;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected HashTagRepository hashTagRepository;

    @Autowired
    protected UserHashTagRepository userHashTagRepository;

    @BeforeEach
    void repositorySetUp() {
        userRepository.save(user);
        hashTagRepository.save(hashTag);
        userHashTagRepository.save(userHashTag);
    }

    protected void entityManagerSetUp() {
        entityManager.flush();
        entityManager.clear();
    }

}