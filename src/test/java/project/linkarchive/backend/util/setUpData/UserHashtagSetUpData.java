package project.linkarchive.backend.util.setUpData;

import org.junit.jupiter.api.BeforeEach;
import project.linkarchive.backend.hashtag.domain.Hashtag;
import project.linkarchive.backend.hashtag.domain.UserHashtag;
import project.linkarchive.backend.link.domain.Link;
import project.linkarchive.backend.link.domain.LinkHashtag;
import project.linkarchive.backend.user.domain.User;

import static project.linkarchive.backend.util.constant.Constants.*;

public class UserHashtagSetUpData extends MockDataGenerator {

    @BeforeEach
    public void setup() {

        user = User.builder()
                .socialId(SOCIAL_ID)
                .nickname(NICKNAME)
                .email(EMAIL)
                .introduce(INTRODUCE)
                .build();

        hashTag = Hashtag.builder()
                .tag(TAG)
                .build();

        userHashTag = UserHashtag.builder()
                .usageCount(USAGE_COUNT_INT)
                .user(user)
                .hashtag(hashTag)
                .build();

        linkHashTag = LinkHashtag.builder()
                .link(link)
                .hashtag(hashTag)
                .build();

        for (int i = 0; i < 10; i++) {
            Link link = Link.builder()
                    .url(URL + i)
                    .title(TITLE)
                    .description(DESCRIPTION)
                    .thumbnail(THUMBNAIL)
                    .bookmarkCount(BOOKMARK_COUNT)
                    .user(user)
                    .build();

            LinkHashtag linkHashTag = LinkHashtag.builder()
                    .link(link)
                    .hashtag(hashTag)
                    .build();

            linkHashtagList.add(linkHashTag);
        }

    }

}