package project.linkarchive.backend;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.bookmark.domain.BookMark;
import project.linkarchive.backend.bookmark.repository.BookMarkRepository;
import project.linkarchive.backend.hashtag.domain.HashTag;
import project.linkarchive.backend.hashtag.repository.HashTagRepository;
import project.linkarchive.backend.link.domain.Link;
import project.linkarchive.backend.link.domain.LinkHashTag;
import project.linkarchive.backend.link.repository.LinkHashTagRepository;
import project.linkarchive.backend.link.repository.LinkRepository;
import project.linkarchive.backend.user.domain.ProfileImage;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.domain.UserHashTag;
import project.linkarchive.backend.user.repository.UserHashTagRepository;
import project.linkarchive.backend.user.repository.UserProfileImageRepository;
import project.linkarchive.backend.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class DataInit {

    private final UserRepository userRepository;
    private final UserProfileImageRepository userProfileImageRepository;
    private final LinkRepository linkRepository;
    private final HashTagRepository hashTagRepository;
    private final LinkHashTagRepository linkHashTagRepository;
    private final UserHashTagRepository userHashTagRepository;
    private final BookMarkRepository bookMarkRepository;

    public DataInit(UserRepository userRepository, UserProfileImageRepository userProfileImageRepository, LinkRepository linkRepository, HashTagRepository hashTagRepository, LinkHashTagRepository linkHashTagRepository, UserHashTagRepository userHashTagRepository, BookMarkRepository bookMarkRepository) {
        this.userRepository = userRepository;
        this.userProfileImageRepository = userProfileImageRepository;
        this.linkRepository = linkRepository;
        this.hashTagRepository = hashTagRepository;
        this.linkHashTagRepository = linkHashTagRepository;
        this.userHashTagRepository = userHashTagRepository;
        this.bookMarkRepository = bookMarkRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initDB() {
        initUser();
        initProfileImage();
        initLink();
        initTag();
        initLinkTag();
        initUserTag();
        initBookMark();
    }

    public void initUser() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            User user = User.builder()
                    .id((long) i)
                    .socialId("kakao" + i)
                    .email(i + "@google.com")
                    .name("user" + i)
                    .introduce(i + "입니다")
                    .build();
            users.add(user);
        }
        userRepository.saveAll(users);
    }

    public void initProfileImage() {
        List<ProfileImage> profileImages = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            User user = userRepository.findById((long) i).orElse(null);

            ProfileImage profileImage = ProfileImage.builder()
                    .id((long) i)
                    .profileImage("profileImage" + i)
                    .user(user)
                    .build();
            profileImages.add(profileImage);
        }
        userProfileImageRepository.saveAll(profileImages);
    }

    public void initLink() {
        List<Link> links = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            User user = userRepository.findById((long) i).orElse(null);
            if (user != null) {
                for (int j = 1; j <= 100; j++) {
                    Link link = Link.builder()
                            .id((long) j)
                            .url("url" + j)
                            .title("title" + j)
                            .description("description" + j)
                            .thumbnail("thumbnail" + j)
                            .bookMarkCount((long) j)
                            .user(user)
                            .build();
                    links.add(link);
                }
            }
        }
        linkRepository.saveAll(links);
    }

    public void initTag() {
        List<HashTag> hashTags = new ArrayList<>();
        for (int i = 1; i <= 500; i++) {
            HashTag hashTag = HashTag.builder()
                    .id((long) i)
                    .tag("tag" + i)
                    .build();
            hashTags.add(hashTag);
        }
        hashTagRepository.saveAll(hashTags);
    }

    public void initLinkTag() {
        int startTagId = 1;
        int endTagId = 5;
        int increment = 5;
        List<LinkHashTag> linkHashTags = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            Link link = linkRepository.findById((long) i).orElse(null);
            if (link != null) {
                for (int j = startTagId; j <= endTagId; j++) {
                    HashTag hashTag = hashTagRepository.findById((long) j).orElse(null);
                    if (hashTag != null) {
                        LinkHashTag linkHashTag = LinkHashTag.builder()
                                .link(link)
                                .hashTag(hashTag)
                                .build();
                        linkHashTags.add(linkHashTag);
                    }
                }
            }
            startTagId += increment;
            endTagId += increment;
        }
        linkHashTagRepository.saveAll(linkHashTags);
    }

    public void initUserTag() {
        int startTagId = 1;
        int endTagId = 50;
        int increment = 50;
        List<UserHashTag> userHashTags = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            User user = userRepository.findById((long) i).orElse(null);
            if (user != null) {
                for (int j = startTagId; j <= endTagId; j++) {
                    HashTag hashTag = hashTagRepository.findById((long) j).orElse(null);
                    if (hashTag != null) {
                        UserHashTag userHashTag = UserHashTag.builder()
                                .usageCount((long) i)
                                .user(user)
                                .hashTag(hashTag)
                                .build();
                        userHashTags.add(userHashTag);
                    }
                }
            }
            startTagId += increment;
            endTagId += increment;
        }
        userHashTagRepository.saveAll(userHashTags);
    }

    public void initBookMark() {
        int startTagId = 1;
        int endTagId = 5;
        int increment = 10;
        List<BookMark> bookMarks = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            User user = userRepository.findById((long) i).orElse(null);
            if (user != null) {
                for (int j = startTagId; j <= endTagId; j++) {
                    Link link = linkRepository.findById((long) j).orElse(null);
                    if (link != null) {
                        BookMark bookMark = BookMark.builder()
                                .user(user)
                                .link(link)
                                .build();
                        bookMarks.add(bookMark);
                    }
                }
            }
            startTagId += increment;
            endTagId += increment;
        }
        bookMarkRepository.saveAll(bookMarks);
    }

}