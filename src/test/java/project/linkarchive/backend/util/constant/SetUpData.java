package project.linkarchive.backend.util.constant;

import org.junit.jupiter.api.BeforeEach;
import project.linkarchive.backend.auth.domain.RefreshToken;
import project.linkarchive.backend.bookmark.domain.BookMark;
import project.linkarchive.backend.hashtag.domain.HashTag;
import project.linkarchive.backend.isLinkRead.domain.IsLinkRead;
import project.linkarchive.backend.link.domain.Link;
import project.linkarchive.backend.link.domain.LinkHashTag;
import project.linkarchive.backend.profileImage.domain.ProfileImage;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.domain.UserHashTag;

import static project.linkarchive.backend.util.constant.Constants.*;

public class SetUpData {

    public User user;
    public ProfileImage profileImage;
    public RefreshToken refreshToken;
    public Link link;
    public HashTag hashTag;
    public UserHashTag userHashTag;
    public LinkHashTag linkHashTag;
    public BookMark bookMark;
    public IsLinkRead isLinkRead;

    @BeforeEach
    public void setup() {

        user = User.builder()
                .socialId(SOCIAL_ID)
                .nickname(NICKNAME)
                .email(EMAIL)
                .introduce(INTRODUCE)
                .build();

        profileImage = ProfileImage.builder()
                .profileImageFilename(PROFILE_IMAGE_FILENAME)
                .user(user)
                .build();

        refreshToken = RefreshToken.builder()
                .refreshToken(REFRESH_TOKEN)
                .agent(AGENT)
                .user(user)
                .build();

        link = Link.builder()
                .url(URL)
                .title(TITLE)
                .description(DESCRIPTION)
                .thumbnail(THUMBNAIL)
                .bookMarkCount(BOOKMARK_COUNT)
                .user(user)
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

        bookMark = BookMark.builder()
                .user(user)
                .link(link)
                .build();

        isLinkRead = IsLinkRead.builder()
                .user(user)
                .link(link)
                .build();

    }

}