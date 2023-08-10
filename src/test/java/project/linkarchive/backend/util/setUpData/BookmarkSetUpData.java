package project.linkarchive.backend.util.setUpData;

import org.junit.jupiter.api.BeforeEach;
import project.linkarchive.backend.bookmark.domain.Bookmark;
import project.linkarchive.backend.link.domain.Link;
import project.linkarchive.backend.user.domain.User;

import static project.linkarchive.backend.util.constant.Constants.*;
import static project.linkarchive.backend.util.constant.Constants.INTRODUCE;

public class BookmarkSetUpData extends MockDataGenerator {

    @BeforeEach
    public void setup() {

        user = User.builder()
                .socialId(SOCIAL_ID)
                .nickname(NICKNAME)
                .email(EMAIL)
                .introduce(INTRODUCE)
                .build();

        link = Link.builder()
                .url(URL)
                .title(TITLE)
                .description(DESCRIPTION)
                .thumbnail(THUMBNAIL)
                .bookmarkCount(BOOKMARK_COUNT)
                .user(user)
                .build();

        bookMark = Bookmark.builder()
                .user(user)
                .link(link)
                .build();

    }

}