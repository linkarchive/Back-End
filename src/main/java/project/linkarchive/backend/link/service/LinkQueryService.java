package project.linkarchive.backend.link.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.bookmark.repository.BookMarkRepositoryImpl;
import project.linkarchive.backend.hashtag.response.TagResponse;
import project.linkarchive.backend.link.domain.UrlHashTag;
import project.linkarchive.backend.link.repository.LinkRepositoryImpl;
import project.linkarchive.backend.link.repository.UrlHashTagRepository;
import project.linkarchive.backend.link.response.linkarchive.ArchiveResponse;
import project.linkarchive.backend.link.response.linkarchive.UserArchiveResponse;
import project.linkarchive.backend.link.response.linkarchive.UserLinkArchiveResponse;
import project.linkarchive.backend.link.response.userLinkList.LinkResponse;
import project.linkarchive.backend.link.response.userLinkList.UserLinkListResponse;
import project.linkarchive.backend.link.response.userLinkList.UserLinkResponse;
import project.linkarchive.backend.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.NOT_FOUND_USER;

@Service
@Transactional(readOnly = true)
public class LinkQueryService {

    private final UrlHashTagRepository urlHashTagRepository;
    private final LinkRepositoryImpl linkRepositoryImpl;
    private final UserRepository userRepository;
    private final BookMarkRepositoryImpl bookMarkRepositoryImpl;

    public LinkQueryService(UrlHashTagRepository urlHashTagRepository, LinkRepositoryImpl linkRepositoryImpl, UserRepository userRepository, BookMarkRepositoryImpl bookMarkRepositoryImpl) {
        this.urlHashTagRepository = urlHashTagRepository;
        this.linkRepositoryImpl = linkRepositoryImpl;
        this.userRepository = userRepository;
        this.bookMarkRepositoryImpl = bookMarkRepositoryImpl;
    }

    public UserLinkListResponse getUserLinkList(Long userId, Pageable pageable, Long lastLinkId) {
        checkUserId(userId);

        List<LinkResponse> linkResponseList = linkRepositoryImpl.getUserLinkList(userId, pageable, lastLinkId);
        List<UserLinkResponse> userLinkResponse = linkResponseList.stream()
                .map(linkResponse -> {
                    List<UrlHashTag> urlHashTagList = urlHashTagRepository.findByLinkId(linkResponse.getLinkId());
                    List<TagResponse> tagList = urlHashTagList.stream()
                            .map(TagResponse::build)
                            .collect(Collectors.toList());

                    return UserLinkResponse.build(linkResponse, tagList);
                }).collect(Collectors.toList());

        return new UserLinkListResponse(userLinkResponse);
    }

    public UserLinkListResponse getMarkedLinkList(Long userId, Long lastLinkId, Pageable pageable) {
        checkUserId(userId);

        List<LinkResponse> linkResponses = bookMarkRepositoryImpl.getMarkLinkList(userId, lastLinkId, pageable);
        List<UserLinkResponse> userLinkResponses = linkResponses.stream()
                .map(linkResponse -> {
                    List<UrlHashTag> urlHashTagList = urlHashTagRepository.findByLinkId(linkResponse.getLinkId());
                    List<TagResponse> tagList = urlHashTagList.stream()
                            .map(TagResponse::build)
                            .collect(Collectors.toList());

                    return UserLinkResponse.build(linkResponse, tagList);
                }).collect(Collectors.toList());

        return new UserLinkListResponse(userLinkResponses);
    }

    public UserLinkArchiveResponse getLinkArchive(Pageable pageable, Long lastLinkId, Long userId) {
        checkUserId(userId);

        List<ArchiveResponse> linkList = linkRepositoryImpl.getLinkArchive(pageable, lastLinkId, userId);
        List<UserArchiveResponse> linkDetailList = linkList.stream()
                .map(archiveResponse -> {
                    List<UrlHashTag> urlHashTagList = urlHashTagRepository.findByLinkId(archiveResponse.getLinkId());
                    List<TagResponse> tagList = urlHashTagList.stream()
                            .map(TagResponse::build)
                            .collect(Collectors.toList());

                    return UserArchiveResponse.build(archiveResponse, tagList);
                }).collect(Collectors.toList());

        return new UserLinkArchiveResponse(linkDetailList);
    }

    private void checkUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }

}