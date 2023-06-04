package project.linkarchive.backend.util.setUpData;

import org.junit.jupiter.api.BeforeEach;
import project.linkarchive.backend.bookmark.domain.BookMark;
import project.linkarchive.backend.link.domain.Link;
import project.linkarchive.backend.user.domain.User;

import static project.linkarchive.backend.util.constant.Constants.*;
import static project.linkarchive.backend.util.constant.Constants.INTRODUCE;

public class BookMarkSetUpData extends SetUpData {

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
                .bookMarkCount(BOOKMARK_COUNT)
                .user(user)
                .build();

        bookMark = BookMark.builder()
                .user(user)
                .link(link)
                .build();

    }

}