package project.linkarchive.backend.util.setUpData;

import org.junit.jupiter.api.BeforeEach;
import project.linkarchive.backend.hashtag.domain.HashTag;
import project.linkarchive.backend.hashtag.domain.UserHashTag;
import project.linkarchive.backend.link.domain.Link;
import project.linkarchive.backend.link.domain.LinkHashTag;
import project.linkarchive.backend.user.domain.User;

import static project.linkarchive.backend.util.constant.Constants.*;

public class UserHashTagSetUpData extends MockDataGenerator {

    @BeforeEach
    public void setup() {

        user = User.builder()
                .socialId(SOCIAL_ID)
                .nickname(NICKNAME)
                .email(EMAIL)
                .introduce(INTRODUCE)
                .build();

        hashTag = HashTag.builder()
                .tag(TAG)
                .build();

        userHashTag = UserHashTag.builder()
                .usageCount(USAGE_COUNT)
                .user(user)
                .hashTag(hashTag)
                .build();

        linkHashTag = LinkHashTag.builder()
                .link(link)
                .hashTag(hashTag)
                .build();

        for (int i = 0; i < 10; i++) {
            Link link = Link.builder()
                    .url(URL + i)
                    .title(TITLE)
                    .description(DESCRIPTION)
                    .thumbnail(THUMBNAIL)
                    .bookMarkCount(BOOKMARK_COUNT)
                    .user(user)
                    .build();

            LinkHashTag linkHashTag = LinkHashTag.builder()
                    .link(link)
                    .hashTag(hashTag)
                    .build();

            linkHashTagList.add(linkHashTag);
        }

    }

}