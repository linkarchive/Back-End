//package project.linkarchive.backend;
//
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import project.linkarchive.backend.bookmark.domain.BookMark;
//import project.linkarchive.backend.bookmark.repository.BookMarkRepository;
//import project.linkarchive.backend.hashtag.domain.HashTag;
//import project.linkarchive.backend.hashtag.repository.HashTagRepository;
//import project.linkarchive.backend.isLinkRead.domain.IsLinkRead;
//import project.linkarchive.backend.isLinkRead.repository.IsLinkReadRepository;
//import project.linkarchive.backend.link.domain.Link;
//import project.linkarchive.backend.link.domain.LinkHashTag;
//import project.linkarchive.backend.link.repository.LinkHashTagRepository;
//import project.linkarchive.backend.link.repository.LinkRepository;
//import project.linkarchive.backend.profileImage.domain.ProfileImage;
//import project.linkarchive.backend.user.domain.User;
//import project.linkarchive.backend.user.domain.UserHashTag;
//import project.linkarchive.backend.user.repository.UserHashTagRepository;
//import project.linkarchive.backend.profileImage.repository.UserProfileImageRepository;
//import project.linkarchive.backend.user.repository.UserRepository;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Random;
//
//@Component
//@Transactional
//public class DataInit {
//
//    private final UserRepository userRepository;
//    private final UserProfileImageRepository userProfileImageRepository;
//    private final LinkRepository linkRepository;
//    private final HashTagRepository hashTagRepository;
//    private final LinkHashTagRepository linkHashTagRepository;
//    private final UserHashTagRepository userHashTagRepository;
//    private final BookMarkRepository bookMarkRepository;
//    private final IsLinkReadRepository isLinkReadRepository;
//
//    public DataInit(UserRepository userRepository, UserProfileImageRepository userProfileImageRepository, LinkRepository linkRepository, HashTagRepository hashTagRepository, LinkHashTagRepository linkHashTagRepository, UserHashTagRepository userHashTagRepository, BookMarkRepository bookMarkRepository,
//                    IsLinkReadRepository isLinkReadRepository) {
//        this.userRepository = userRepository;
//        this.userProfileImageRepository = userProfileImageRepository;
//        this.linkRepository = linkRepository;
//        this.hashTagRepository = hashTagRepository;
//        this.linkHashTagRepository = linkHashTagRepository;
//        this.userHashTagRepository = userHashTagRepository;
//        this.bookMarkRepository = bookMarkRepository;
//        this.isLinkReadRepository = isLinkReadRepository;
//    }
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void initDB() {
//        initUser();
//        initProfileImage();
//        initLink();
//        initTag();
//        initLinkTag();
//        initUserTag();
//        initBookMark();
//        initIsLinkRead();
//    }
//
//    public void initUser() {
//        List<User> users = new ArrayList<>();
//        for (int i = 1; i <= 10; i++) {
//            User user = User.builder()
//                    .socialId("kakao" + i)
//                    .email(i + "@google.com")
//                    .nickname("nickname" + i)
//                    .introduce(i + "입니다")
//                    .build();
//            users.add(user);
//        }
//
//        userRepository.saveAll(users);
//    }
//
//    public void initProfileImage() {
//        List<ProfileImage> profileImages = new ArrayList<>();
//        List<User> users = userRepository.findAll();
//        for (int i = 0; i < users.size(); i++) {
//            ProfileImage profileImage = ProfileImage.builder()
//                    .profileImageFilename("profileImage" + (i + 1))
//                    .user(users.get(i))
//                    .build();
//            profileImages.add(profileImage);
//        }
//        userProfileImageRepository.saveAll(profileImages);
//    }
//
//    public void initLink() {
//        int startLinkId = 1;
//        int linksPerUser = 10;
//        List<Link> links = new ArrayList<>();
//        List<User> users = userRepository.findAll();
//
//        for (User user : users) {
//            for (int j = 0; j < linksPerUser; j++) {
//                int linkId = startLinkId + j;
//                Link link = Link.builder()
//                        .url("url" + linkId)
//                        .title("title" + linkId)
//                        .description("description" + linkId)
//                        .thumbnail("thumbnail" + linkId)
//                        .bookMarkCount((long) linkId)
//                        .user(user)
//                        .build();
//                links.add(link);
//            }
//            startLinkId += linksPerUser;
//        }
//
//        linkRepository.saveAll(links);
//    }
//
//    public void initTag() {
//        List<HashTag> hashTags = new ArrayList<>();
//        for (int i = 1; i <= 100; i++) {
//            HashTag hashTag = HashTag.builder()
//                    .id((long) i)
//                    .tag("tag" + i)
//                    .build();
//            hashTags.add(hashTag);
//        }
//
//        hashTagRepository.saveAll(hashTags);
//    }
//
//    public void initLinkTag() {
//        int tagsPerLink = 5;
//        List<HashTag> allHashTags = hashTagRepository.findAll();
//        List<LinkHashTag> linkHashTags = new ArrayList<>();
//
//        List<Link> links = linkRepository.findAll();
//        for (Link link : links) {
//            Collections.shuffle(allHashTags);
//            List<HashTag> linkTags = allHashTags.subList(0, tagsPerLink);
//            for (HashTag hashTag : linkTags) {
//                LinkHashTag linkHashTag = LinkHashTag.builder()
//                        .link(link)
//                        .hashTag(hashTag)
//                        .build();
//                linkHashTags.add(linkHashTag);
//            }
//        }
//
//        linkHashTagRepository.saveAll(linkHashTags);
//    }
//
//    public void initUserTag() {
//        Random rand = new Random();
//
//        List<User> users = userRepository.findAll();
//        List<HashTag> hashTags = hashTagRepository.findAll();
//
//        List<UserHashTag> userHashTags = new ArrayList<>();
//
//        for (User user : users) {
//            int numberOfTags = 1 + rand.nextInt(10);
//
//            for (int j = 0; j < numberOfTags; j++) {
//                HashTag hashTag = hashTags.get(rand.nextInt(hashTags.size()));
//
//                UserHashTag userHashTag = UserHashTag.builder()
//                        .usageCount((long) (1 + rand.nextInt(100)))
//                        .user(user)
//                        .hashTag(hashTag)
//                        .build();
//
//                userHashTags.add(userHashTag);
//            }
//        }
//
//        userHashTagRepository.saveAll(userHashTags);
//    }
//
//    public void initBookMark() {
//        Random random = new Random();
//        List<BookMark> bookMarks = new ArrayList<>();
//        List<User> users = userRepository.findAll();
//        List<Link> links = linkRepository.findAll();
//
//        for (User user : users) {
//            int numBookMarks = random.nextInt(links.size()) + 1;
//            for (int j = 0; j < numBookMarks; j++) {
//                int linkIndex = random.nextInt(links.size());
//                Link link = links.get(linkIndex);
//
//                BookMark bookMark = BookMark.builder()
//                        .user(user)
//                        .link(link)
//                        .build();
//                bookMarks.add(bookMark);
//            }
//        }
//        bookMarkRepository.saveAll(bookMarks);
//    }
//
//    public void initIsLinkRead() {
//        Random random = new Random();
//        List<IsLinkRead> isLinkReads = new ArrayList<>();
//        List<User> users = userRepository.findAll();
//        List<Link> links = linkRepository.findAll();
//
//        for (User user : users) {
//            int numReadLinks = random.nextInt(links.size()) + 1;
//            for (int j = 0; j < numReadLinks; j++) {
//                int linkIndex = random.nextInt(links.size());
//                Link link = links.get(linkIndex);
//
//                IsLinkRead isLinkRead = IsLinkRead.builder()
//                        .user(user)
//                        .link(link)
//                        .build();
//                isLinkReads.add(isLinkRead);
//            }
//        }
//        isLinkReadRepository.saveAll(isLinkReads);
//    }
//
//}