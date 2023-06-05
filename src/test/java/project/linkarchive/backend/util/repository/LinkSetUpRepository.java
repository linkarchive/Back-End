package project.linkarchive.backend.util.repository;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import project.linkarchive.backend.link.repository.LinkRepository;
import project.linkarchive.backend.user.repository.UserRepository;
import project.linkarchive.backend.util.setUpData.LinkSetUpData;

import javax.persistence.EntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LinkSetUpRepository extends LinkSetUpData {

    @Autowired
    protected EntityManager entityManager;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected LinkRepository linkRepository;

    @BeforeEach
    void repositorySetUp() {
        userRepository.save(user);
        linkRepository.save(link);
    }

    protected void entityManagerSetUp() {
        entityManager.flush();
        entityManager.clear();
    }

}