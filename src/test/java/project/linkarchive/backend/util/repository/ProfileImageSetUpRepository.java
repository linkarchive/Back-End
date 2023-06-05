package project.linkarchive.backend.util.repository;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import project.linkarchive.backend.profileImage.repository.ProfileImageRepository;
import project.linkarchive.backend.util.setUpData.ProfileImageSetUpData;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProfileImageSetUpRepository extends ProfileImageSetUpData {

    @Autowired
    protected ProfileImageRepository profileImageRepository;

    @BeforeEach
    void repositorySetUp() {
        profileImageRepository.save(profileImage);
    }

}