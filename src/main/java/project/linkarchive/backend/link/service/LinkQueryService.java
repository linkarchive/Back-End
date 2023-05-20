package project.linkarchive.backend.link.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.bookmark.repository.BookMarkRepositoryImpl;
import project.linkarchive.backend.hashtag.response.TagResponse;
import project.linkarchive.backend.link.domain.LinkHashTag;
import project.linkarchive.backend.link.repository.LinkHashTagRepository;
import project.linkarchive.backend.link.repository.LinkRepositoryImpl;
import project.linkarchive.backend.link.response.linkList.LinkResponse;
import project.linkarchive.backend.link.response.linkList.UserLinkListResponse;
import project.linkarchive.backend.link.response.linkList.UserLinkResponse;
import project.linkarchive.backend.link.response.linkarchive.ArchiveResponse;
import project.linkarchive.backend.link.response.linkarchive.UserArchiveResponse;
import project.linkarchive.backend.link.response.linkarchive.UserLinkArchiveResponse;
import project.linkarchive.backend.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.NOT_FOUND_USER;

@Service
@Transactional(readOnly = true)
public class LinkQueryService {

    private final LinkHashTagRepository linkHashTagRepository;
    private final LinkRepositoryImpl linkRepositoryImpl;
    private final UserRepository userRepository;
    private final BookMarkRepositoryImpl bookMarkRepositoryImpl;

    public LinkQueryService(LinkHashTagRepository linkHashTagRepository, LinkRepositoryImpl linkRepositoryImpl, UserRepository userRepository, BookMarkRepositoryImpl bookMarkRepositoryImpl) {
        this.linkHashTagRepository = linkHashTagRepository;
        this.linkRepositoryImpl = linkRepositoryImpl;
        this.userRepository = userRepository;
        this.bookMarkRepositoryImpl = bookMarkRepositoryImpl;
    }

    public UserLinkListResponse getUserLinkList(Long userId, Pageable pageable, Long lastLinkId) {
        checkUserId(userId);

        List<LinkResponse> linkResponseList = linkRepositoryImpl.getUserLinkList(userId, pageable, lastLinkId);

        boolean hasNext = isHasNextLinkList(pageable, linkResponseList);

        List<UserLinkResponse> userLinkResponse = linkResponseList.stream()
                .map(linkResponse -> {
                    List<LinkHashTag> linkHashTagList = linkHashTagRepository.findByLinkId(linkResponse.getLinkId());
                    List<TagResponse> tagList = linkHashTagList.stream()
                            .map(TagResponse::build)
                            .collect(Collectors.toList());

                    return UserLinkResponse.build(linkResponse, tagList);
                }).collect(Collectors.toList());

        return new UserLinkListResponse(userLinkResponse, hasNext);
    }

    public UserLinkListResponse getMarkedLinkList(Long userId, Long lastLinkId, Pageable pageable) {
        checkUserId(userId);

        List<LinkResponse> linkResponseList = bookMarkRepositoryImpl.getMarkLinkList(userId, lastLinkId, pageable);

        List<UserLinkResponse> userLinkResponseList = linkResponseList.stream()
                .map(linkResponse -> {
                    List<LinkHashTag> linkHashTagList = linkHashTagRepository.findByLinkId(linkResponse.getLinkId());
                    List<TagResponse> tagList = linkHashTagList.stream()
                            .map(TagResponse::build)
                            .collect(Collectors.toList());

                    return UserLinkResponse.build(linkResponse, tagList);
                }).collect(Collectors.toList());

        return new UserLinkListResponse(userLinkResponseList);
    }

    public UserLinkArchiveResponse getLinkArchive(Pageable pageable, Long lastLinkId, Long userId) {
        checkUserId(userId);

        List<ArchiveResponse> archiveResponseList = linkRepositoryImpl.getLinkArchive(pageable, lastLinkId, userId);

        boolean hasNext = isHasNextArchiveList(pageable, archiveResponseList);

        List<UserArchiveResponse> userArchiveResponseList = archiveResponseList.stream()
                .map(archiveResponse -> {
                    List<LinkHashTag> linkHashTagList = linkHashTagRepository.findByLinkId(archiveResponse.getLinkId());
                    List<TagResponse> tagList = linkHashTagList.stream()
                            .map(TagResponse::build)
                            .collect(Collectors.toList());

                    return UserArchiveResponse.build(archiveResponse, tagList);
                }).collect(Collectors.toList());


        return new UserLinkArchiveResponse(userArchiveResponseList, hasNext);
    }

    private void checkUserId(Long userId) {
        userRepository.findById(userId)
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

    private boolean isHasNextArchiveList(Pageable pageable, List<ArchiveResponse> archiveResponseList) {
        boolean hasNext = false;
        if (archiveResponseList.size() > pageable.getPageSize()) {
            archiveResponseList.remove(archiveResponseList.size() - 1);
            hasNext = true;
        }
        return hasNext;
    }

}