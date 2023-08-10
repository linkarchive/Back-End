package project.linkarchive.backend.util.repository;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import project.linkarchive.backend.bookmark.repository.BookMarkRepository;
import project.linkarchive.backend.link.repository.LinkRepository;
import project.linkarchive.backend.user.repository.UserRepository;
import project.linkarchive.backend.util.setUpData.BookmarkSetUpData;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookmarkSetUpRepository extends BookmarkSetUpData {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected LinkRepository linkRepository;

    @Autowired
    protected BookMarkRepository bookMarkRepository;

    @BeforeEach
    void repositorySetUp() {
        userRepository.save(user);
        linkRepository.save(link);
        bookMarkRepository.save(bookMark);
    }

}
