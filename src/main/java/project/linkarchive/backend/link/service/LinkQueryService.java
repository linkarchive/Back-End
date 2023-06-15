package project.linkarchive.backend.link.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.bookmark.repository.BookMarkRepository;
import project.linkarchive.backend.hashtag.response.TagResponse;
import project.linkarchive.backend.isLinkRead.repository.IsLinkReadRepository;
import project.linkarchive.backend.link.domain.LinkHashTag;
import project.linkarchive.backend.link.repository.LinkHashTagRepository;
import project.linkarchive.backend.link.repository.LinkRepositoryImpl;
import project.linkarchive.backend.link.response.linkList.LinkResponse;
import project.linkarchive.backend.link.response.linkList.UserLinkListResponse;
import project.linkarchive.backend.link.response.linkList.UserLinkResponse;
import project.linkarchive.backend.link.response.linkarchive.ArchiveResponse;
import project.linkarchive.backend.link.response.linkarchive.UserArchiveResponse;
import project.linkarchive.backend.link.response.linkarchive.UserLinkArchiveResponse;
import project.linkarchive.backend.s3.S3Uploader;
import project.linkarchive.backend.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import static project.linkarchive.backend.advice.data.DataConstants.IMAGE_EXPIRATION_TIME;
import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.NOT_FOUND_USER;

@Service
@Transactional(readOnly = true)
public class LinkQueryService {

    private final S3Uploader s3Uploader;
    private final UserRepository userRepository;
    private final LinkHashTagRepository linkHashTagRepository;
    private final IsLinkReadRepository isLinkReadRepository;
    private final BookMarkRepository bookMarkRepository;
    private final LinkRepositoryImpl linkRepositoryImpl;

    public LinkQueryService(S3Uploader s3Uploader, UserRepository userRepository, LinkHashTagRepository linkHashTagRepository, IsLinkReadRepository isLinkReadRepository, BookMarkRepository bookMarkRepository, LinkRepositoryImpl linkRepositoryImpl) {
        this.s3Uploader = s3Uploader;
        this.userRepository = userRepository;
        this.linkHashTagRepository = linkHashTagRepository;
        this.isLinkReadRepository = isLinkReadRepository;
        this.bookMarkRepository = bookMarkRepository;
        this.linkRepositoryImpl = linkRepositoryImpl;
    }

    public UserLinkListResponse getMyLinkList(Long userId, Pageable pageable, Long lastLinkId, String tag) {
        validationUserById(userId);

        List<LinkResponse> linkResponseList = linkRepositoryImpl.getMyLinkList(userId, pageable, lastLinkId, tag);

        List<UserLinkResponse> userLinkResponse = linkResponseList.stream()
                .map(linkResponse -> {
                    List<LinkHashTag> linkHashTagList = linkHashTagRepository.findByLinkId(linkResponse.getLinkId());
                    List<TagResponse> tagList = linkHashTagList.stream()
                            .map(TagResponse::build)
                            .collect(Collectors.toList());

                    Boolean isRead = isLinkReadRepository.existsByLinkIdAndUserId(linkResponse.getLinkId(), userId);
                    Boolean isMark = bookMarkRepository.existsByLinkIdAndUserId(linkResponse.getLinkId(), userId);

                    return UserLinkResponse.build(linkResponse, isRead, isMark, tagList);
                }).collect(Collectors.toList());

        boolean hasNext = isHasNextLinkList(pageable, linkResponseList);

        return new UserLinkListResponse(userLinkResponse, hasNext);
    }

    public UserLinkListResponse getPublicUserLinkList(String nickname, Pageable pageable, Long lastLinkId, String tag) {
        validationUserByNickname(nickname);

        List<LinkResponse> linkResponseList = linkRepositoryImpl.getUserLinkList(nickname, pageable, lastLinkId, tag);

        List<UserLinkResponse> userLinkResponse = linkResponseList.stream()
                .map(linkResponse -> {
                    List<LinkHashTag> linkHashTagList = linkHashTagRepository.findByLinkId(linkResponse.getLinkId());
                    List<TagResponse> tagList = linkHashTagList.stream()
                            .map(TagResponse::build)
                            .collect(Collectors.toList());

                    return UserLinkResponse.build(linkResponse, false, false, tagList);
                }).collect(Collectors.toList());

        boolean hasNext = isHasNextLinkList(pageable, linkResponseList);

        return new UserLinkListResponse(userLinkResponse, hasNext);
    }

    public UserLinkListResponse getAuthenticatedUserLinkList(String nickname, Pageable pageable, Long lastLinkId, Long loginUserId, String tag) {
        validationUserByNickname(nickname);

        List<LinkResponse> linkResponseList = linkRepositoryImpl.getUserLinkList(nickname, pageable, lastLinkId, tag);

        List<UserLinkResponse> userLinkResponse = linkResponseList.stream()
                .map(linkResponse -> {
                    List<LinkHashTag> linkHashTagList = linkHashTagRepository.findByLinkId(linkResponse.getLinkId());
                    List<TagResponse> tagList = linkHashTagList.stream()
                            .map(TagResponse::build)
                            .collect(Collectors.toList());

                    Boolean isRead = isLinkReadRepository.existsByLinkIdAndUserId(linkResponse.getLinkId(), loginUserId);
                    Boolean isMark = bookMarkRepository.existsByLinkIdAndUserId(linkResponse.getLinkId(), loginUserId);

                    return UserLinkResponse.build(linkResponse, isRead, isMark, tagList);
                }).collect(Collectors.toList());

        boolean hasNext = isHasNextLinkList(pageable, linkResponseList);

        return new UserLinkListResponse(userLinkResponse, hasNext);
    }

    public UserLinkArchiveResponse getPublicLinkArchive(Pageable pageable, Long lastLinkId, String tag) {
        List<ArchiveResponse> archiveResponseList = linkRepositoryImpl.getLinkArchive(pageable, lastLinkId, tag);

        boolean hasNext = isHasNextLinkArchive(pageable, archiveResponseList);

        List<UserArchiveResponse> userArchiveResponseList = archiveResponseList.stream()
                .map(archiveResponse -> {
                    List<LinkHashTag> linkHashTagList = linkHashTagRepository.findByLinkId(archiveResponse.getLinkId());
                    List<TagResponse> tagList = linkHashTagList.stream()
                            .map(TagResponse::build)
                            .collect(Collectors.toList());

                    String profileImage = generateProfileImageUrl(archiveResponse.getProfileImage());

                    return UserArchiveResponse.build(archiveResponse, profileImage, false, false, tagList);
                }).collect(Collectors.toList());

        return new UserLinkArchiveResponse(userArchiveResponseList, hasNext);
    }

    public UserLinkArchiveResponse getAuthenticatedLinkArchive(Pageable pageable, Long lastLinkId, Long loginUserId, String tag) {
        List<ArchiveResponse> archiveResponseList = linkRepositoryImpl.getLinkArchive(pageable, lastLinkId, tag);

        boolean hasNext = isHasNextLinkArchive(pageable, archiveResponseList);

        List<UserArchiveResponse> userArchiveResponseList = archiveResponseList.stream()
                .map(archiveResponse -> {
                    List<LinkHashTag> linkHashTagList = linkHashTagRepository.findByLinkId(archiveResponse.getLinkId());
                    List<TagResponse> tagList = linkHashTagList.stream()
                            .map(TagResponse::build)
                            .collect(Collectors.toList());

                    Boolean isRead = isLinkReadRepository.existsByLinkIdAndUserId(archiveResponse.getLinkId(), loginUserId);
                    Boolean isMark = bookMarkRepository.existsByLinkIdAndUserId(archiveResponse.getLinkId(), loginUserId);

                    String profileImage = generateProfileImageUrl(archiveResponse.getProfileImage());

                    return UserArchiveResponse.build(archiveResponse, profileImage, isRead, isMark, tagList);
                }).collect(Collectors.toList());

        return new UserLinkArchiveResponse(userArchiveResponseList, hasNext);
    }

    private void validationUserById(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }

    private void validationUserByNickname(String nickname) {
        userRepository.findByNickname(nickname)
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

    private String generateProfileImageUrl(String profileImageFilename) {
        return s3Uploader.generatePresignedProfileImageUrl(
                        profileImageFilename,
                        IMAGE_EXPIRATION_TIME)
                .toString();
    }

}