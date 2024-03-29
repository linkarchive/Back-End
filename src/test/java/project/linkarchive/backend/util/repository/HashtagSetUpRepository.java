package project.linkarchive.backend.util.repository;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import project.linkarchive.backend.hashtag.repository.HashTagRepository;
import project.linkarchive.backend.util.setUpData.HashtagSetUpData;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class HashtagSetUpRepository extends HashtagSetUpData {

    @Autowired
    protected HashTagRepository hashTagRepository;

    @BeforeEach
    void repositorySetUp() {
        hashTagRepository.save(hashTag);
    }

}