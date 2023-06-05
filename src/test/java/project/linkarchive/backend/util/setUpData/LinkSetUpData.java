package project.linkarchive.backend.util.setUpData;

import org.junit.jupiter.api.BeforeEach;
import project.linkarchive.backend.hashtag.domain.HashTag;
import project.linkarchive.backend.link.domain.Link;
import project.linkarchive.backend.link.request.CreateLinkRequest;
import project.linkarchive.backend.user.domain.User;

import static project.linkarchive.backend.util.constant.Constants.*;

public class LinkSetUpData extends SetUpData {

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

        for (int i = 0; i < 10; i++) {
            HashTag hashTag = HashTag.builder()
                    .tag(TAG + i)
                    .build();

            tagList.add(hashTag.getTag());
        }

        createLinkRequest = new CreateLinkRequest(URL, TITLE, DESCRIPTION, THUMBNAIL, tagList);

    }

}