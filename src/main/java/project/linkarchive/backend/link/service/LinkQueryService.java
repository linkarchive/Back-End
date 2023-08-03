package project.linkarchive.backend.link.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.bookmark.repository.BookMarkRepository;
import project.linkarchive.backend.hashtag.response.TagListResponse;
import project.linkarchive.backend.hashtag.response.TagResponse;
import project.linkarchive.backend.isLinkRead.repository.IsLinkReadRepository;
import project.linkarchive.backend.link.domain.Link;
import project.linkarchive.backend.link.domain.LinkHashTag;
import project.linkarchive.backend.link.repository.LinkHashTagRepository;
import project.linkarchive.backend.link.repository.LinkRepository;
import project.linkarchive.backend.link.repository.LinkRepositoryImpl;
import project.linkarchive.backend.link.response.linkList.LinkResponse;
import project.linkarchive.backend.link.response.linkList.UserLinkListResponse;
import project.linkarchive.backend.link.response.linkList.UserLinkResponse;
import project.linkarchive.backend.link.response.linkarchive.ArchiveResponse;
import project.linkarchive.backend.link.response.linkarchive.UserArchiveResponse;
import project.linkarchive.backend.link.response.linkarchive.UserLinkArchiveResponse;
import project.linkarchive.backend.link.response.trash.TrashLinkListResponse;
import project.linkarchive.backend.link.response.trash.TrashLinkResponse;
import project.linkarchive.backend.link.response.trash.UserTrashLinkListResponse;
import project.linkarchive.backend.s3.S3Uploader;
import project.linkarchive.backend.security.AuthInfo;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.UserRepository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static project.linkarchive.backend.advice.data.DataConstants.IMAGE_EXPIRATION_TIME;
import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.NOT_FOUND_USER;

@Service
@Transactional(readOnly = true)
public class LinkQueryService {

    private final S3Uploader s3Uploader;
    private final UserRepository userRepository;
    private final LinkRepository linkRepository;
    private final LinkHashTagRepository linkHashTagRepository;
    private final IsLinkReadRepository isLinkReadRepository;
    private final BookMarkRepository bookMarkRepository;
    private final LinkRepositoryImpl linkRepositoryImpl;

    public LinkQueryService(
            S3Uploader s3Uploader,
            UserRepository userRepository,
            LinkRepository linkRepository,
            LinkHashTagRepository linkHashTagRepository,
            IsLinkReadRepository isLinkReadRepository,
            BookMarkRepository bookMarkRepository,
            LinkRepositoryImpl linkRepositoryImpl
    ) {
        this.s3Uploader = s3Uploader;
        this.userRepository = userRepository;
        this.linkRepository = linkRepository;
        this.linkHashTagRepository = linkHashTagRepository;
        this.isLinkReadRepository = isLinkReadRepository;
        this.bookMarkRepository = bookMarkRepository;
        this.linkRepositoryImpl = linkRepositoryImpl;
    }

    public UserLinkListResponse getMyLinkList(Long tagId, Long linkId, Pageable pageable, Long loginUserId) {
        getUserById(loginUserId);

        List<LinkResponse> linkResponseList = linkRepositoryImpl.getMyLinkList(tagId, linkId, pageable, loginUserId);

        List<UserLinkResponse> userLinkResponse = linkResponseList.stream()
                .map(linkResponse -> {
                    List<LinkHashTag> linkHashTagList = linkHashTagRepository.findByLinkId(linkResponse.getLinkId());
                    List<TagResponse> tagList = linkHashTagList.stream()
                            .map(TagResponse::create)
                            .collect(Collectors.toList());

                    Boolean isRead = isLinkReadRepository.existsByLinkIdAndUserId(linkResponse.getLinkId(), loginUserId);
                    Boolean isMark = bookMarkRepository.existsByLinkIdAndUserId(linkResponse.getLinkId(), loginUserId);

                    return UserLinkResponse.create(linkResponse, isRead, isMark, tagList);
                }).collect(Collectors.toList());

        boolean hasNext = isHasNextLinkList(pageable, linkResponseList);

        return new UserLinkListResponse(userLinkResponse, hasNext);
    }

    public UserLinkListResponse getUserLinkList(Long userId, Long tagId, Long linkId, Pageable pageable, AuthInfo authInfo) {
        getUserById(userId);

        Long loginUserId = authInfo != null ? authInfo.getId() : null;

        List<LinkResponse> linkResponseList = linkRepositoryImpl.getUserLinkList(userId, tagId, linkId, pageable);
        List<UserLinkResponse> userLinkResponse = linkResponseList.stream()
                .map(linkResponse -> {
                    List<LinkHashTag> linkHashTagList = linkHashTagRepository.findByLinkId(linkResponse.getLinkId());
                    List<TagResponse> tagList = linkHashTagList.stream()
                            .map(TagResponse::create)
                            .collect(Collectors.toList());

                    Boolean isRead = (loginUserId != null) ? isLinkReadRepository.existsByLinkIdAndUserId(linkResponse.getLinkId(), loginUserId) : false;
                    Boolean isMark = (loginUserId != null) ? bookMarkRepository.existsByLinkIdAndUserId(linkResponse.getLinkId(), loginUserId) : false;

                    return UserLinkResponse.create(linkResponse, isRead, isMark, tagList);
                }).collect(Collectors.toList());

        boolean hasNext = isHasNextLinkList(pageable, linkResponseList);

        return new UserLinkListResponse(userLinkResponse, hasNext);
    }

    public UserLinkArchiveResponse getLinkArchive(Long tagId, Long linkId, Pageable pageable, AuthInfo authInfo) {
        List<ArchiveResponse> archiveResponseList = linkRepositoryImpl.getLinkArchive(tagId, linkId, pageable);

        Long loginUserId = authInfo != null ? authInfo.getId() : null;

        List<UserArchiveResponse> userArchiveResponseList = archiveResponseList.stream()
                .map(archiveResponse -> {
                    List<LinkHashTag> linkHashTagList = linkHashTagRepository.findByLinkId(archiveResponse.getLinkId());
                    List<TagResponse> tagList = linkHashTagList.stream()
                            .map(TagResponse::create)
                            .collect(Collectors.toList());

                    Boolean isRead = (loginUserId != null) ? isLinkReadRepository.existsByLinkIdAndUserId(archiveResponse.getLinkId(), loginUserId) : false;
                    Boolean isMark = (loginUserId != null) ? bookMarkRepository.existsByLinkIdAndUserId(archiveResponse.getLinkId(), loginUserId) : false;

                    String profileImage = generateProfileImageUrl(archiveResponse.getProfileImage());

                    return UserArchiveResponse.create(archiveResponse, profileImage, isRead, isMark, tagList);
                }).collect(Collectors.toList());

        boolean hasNext = isHasNextLinkArchive(pageable, archiveResponseList);

        return new UserLinkArchiveResponse(userArchiveResponseList, hasNext);
    }

    public UserTrashLinkListResponse getTrashLinkList(Long tagId, Long linkId, Pageable pageable, Long loginUserId) {
        List<TrashLinkResponse> trashLinkResponseList = linkRepositoryImpl.getTrashLinkList(tagId, linkId, pageable, loginUserId);

        List<TrashLinkListResponse> trashLinkListResponseList = trashLinkResponseList.stream()
                .map(trashLinkResponse -> {
                    List<LinkHashTag> linkHashTagList = linkHashTagRepository.findByLinkId(trashLinkResponse.getLinkId());
                    List<TagResponse> tagList = linkHashTagList.stream()
                            .map(TagResponse::create)
                            .collect(Collectors.toList());

                    return TrashLinkListResponse.create(trashLinkResponse, tagList);
                }).collect(Collectors.toList());

        boolean hasNext = isHasNextLinkTrash(pageable, trashLinkResponseList);

        return new UserTrashLinkListResponse(trashLinkListResponseList, hasNext);
    }

    public TagListResponse getLinkTagList(Long userId) {
        User user = findUserById(userId);

        List<Link> linkList = linkRepository.findByUserId(user.getId());
        Map<String, Long> tagIdMap = new HashMap<>();
        Map<String, Integer> tagCountMap = new HashMap<>();

        linkList.forEach(link -> {
            List<LinkHashTag> linkHashTagList = linkHashTagRepository.findByLinkId(link.getId());
            linkHashTagList.forEach(linkHashTag -> {
                Long tagId = linkHashTag.getHashTag().getId();
                String tagName = linkHashTag.getHashTag().getTag();
                tagIdMap.put(tagName, tagId);
                tagCountMap.put(tagName, tagCountMap.getOrDefault(tagName, 0) + 1);
            });
        });

        List<TagResponse> tagList = tagCountMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(entry -> new TagResponse(tagIdMap.get(entry.getKey()), entry.getKey()))
                .collect(Collectors.toList());

        return new TagListResponse(tagList);
    }

    private void getUserById(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }

    private boolean isHasNextLinkList(Pageable pageable, List<LinkResponse> linkResponseList) {
        boolean hasNext = false;
        if (linkResponseList.size() > pageable.getPageSize()) {
            linkResponseList.remove(linkResponseList.size() - 1);
            hasNext = true;
        }

        return hasNext;
    }

    private boolean isHasNextLinkArchive(Pageable pageable, List<ArchiveResponse> archiveResponseList) {
        boolean hasNext = false;
        if (archiveResponseList.size() > pageable.getPageSize()) {
            archiveResponseList.remove(archiveResponseList.size() - 1);
            hasNext = true;
        }

        return hasNext;
    }

    private boolean isHasNextLinkTrash(Pageable pageable, List<TrashLinkResponse> trashLinkResponseList) {
        boolean hasNext = false;
        if (trashLinkResponseList.size() > pageable.getPageSize()) {
            trashLinkResponseList.remove(trashLinkResponseList.size() - 1);
            hasNext = true;
        }

        return hasNext;
    }

    private String generateProfileImageUrl(String profileImageFilename) {
        return s3Uploader.generatePresignedProfileImageUrl(
                        profileImageFilename,
                        IMAGE_EXPIRATION_TIME)
                .toString();
    }

}