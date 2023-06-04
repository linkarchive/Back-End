package project.linkarchive.backend.util.setUpData;

import org.junit.jupiter.api.BeforeEach;
import project.linkarchive.backend.hashtag.domain.HashTag;
import project.linkarchive.backend.hashtag.domain.UserHashTag;
import project.linkarchive.backend.link.domain.LinkHashTag;
import project.linkarchive.backend.user.domain.User;

import static project.linkarchive.backend.util.constant.Constants.*;

public class UserHashTagSetUpData extends SetUpData {

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
            linkHashTagList.add(linkHashTag);
        }

    }

}