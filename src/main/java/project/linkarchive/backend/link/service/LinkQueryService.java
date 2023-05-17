package project.linkarchive.backend.link.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.BusinessException;
import project.linkarchive.backend.advice.exception.ExceptionCodeConst;
import project.linkarchive.backend.bookmark.repository.BookMarkRepositoryImpl;
import project.linkarchive.backend.hashtag.response.TagListDetailResponse;
import project.linkarchive.backend.link.domain.UrlHashTag;
import project.linkarchive.backend.link.repository.UrlHashTagRepository;
import project.linkarchive.backend.link.repository.UrlRepositoryImpl;
import project.linkarchive.backend.link.response.UserLinkList.LinkResponse;
import project.linkarchive.backend.link.response.UserLinkList.TagResponse;
import project.linkarchive.backend.link.response.UserLinkList.UserLinkListResponse;
import project.linkarchive.backend.link.response.UserLinkList.UserLinkResponse;
import project.linkarchive.backend.link.response.linkList.UserExcludedLinkListDetailResponse;
import project.linkarchive.backend.link.response.linkList.UserExcludedLinkListResponse;
import project.linkarchive.backend.link.response.linkList.UserExcludedLinkTagListDetailResponse;
import project.linkarchive.backend.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class LinkQueryService {

    private final UrlHashTagRepository urlHashTagRepository;
    private final UrlRepositoryImpl urlRepositoryImpl;
    private final UserRepository userRepository;
    private final BookMarkRepositoryImpl bookMarkRepositoryImpl;

    public LinkQueryService(UrlHashTagRepository urlHashTagRepository, UrlRepositoryImpl urlRepositoryImpl, UserRepository userRepository, BookMarkRepositoryImpl bookMarkRepositoryImpl) {
        this.urlHashTagRepository = urlHashTagRepository;
        this.urlRepositoryImpl = urlRepositoryImpl;
        this.userRepository = userRepository;
        this.bookMarkRepositoryImpl = bookMarkRepositoryImpl;
    }

    public UserLinkListResponse getUserLinkList(Long userId, Pageable pageable, Long lastUrlId) {
        checkUserId(userId);
        List<LinkResponse> linkResponseList = urlRepositoryImpl.getUserLinkList(userId, pageable, lastUrlId);
        List<UserLinkResponse> userLinkResponse = linkResponseList.stream()
                .map(link -> {
                    List<UrlHashTag> urlHashTagList = urlHashTagRepository.findByLinkId(link.getUrlId());
                    List<TagResponse> tagResponse = urlHashTagList.stream()
                            .map(urlHashTag -> new TagResponse(
                                    urlHashTag.getHashTag().getTag()))
                            .collect(Collectors.toList());

                    return new UserLinkResponse(
                            link.getUrlId(),
                            link.getLink(),
                            link.getTitle(),
                            link.getDescription(),
                            link.getThumbnail(),
                            link.getBookMarkCount(),
                            tagResponse
                    );
                }).collect(Collectors.toList());

        return new UserLinkListResponse(userLinkResponse);
    }

    public UserExcludedLinkListResponse getLinkList(Pageable pageable, Long lastUrlId, Long userId) {
        //FIXME 기능에 초점을 둬서 쿼리 성능이 좋지 않아요.
        List<UserExcludedLinkListDetailResponse> linkList = urlRepositoryImpl.getLinkList(pageable, lastUrlId, userId);
        List<UserExcludedLinkTagListDetailResponse> linkDetailList = linkList.stream()
                .map(url -> {
                    List<UrlHashTag> urlHashTagList = urlHashTagRepository.findByLinkId(url.getUrlId());
                    List<TagListDetailResponse> tagListDetailResponseList = urlHashTagList.stream()
                            .map(urlHashTag -> TagListDetailResponse.of(urlHashTag))
                            .collect(Collectors.toList());
                    return UserExcludedLinkTagListDetailResponse.of(url, tagListDetailResponseList);
                }).collect(Collectors.toList());

        return new UserExcludedLinkListResponse(linkDetailList);
    }

    public UserLinkListResponse getMarkedLinkList(Long userId, Long lastUrlId, Pageable pageable) {

        checkUserId(userId);

        List<LinkResponse> linkResponses = bookMarkRepositoryImpl.getMarkLinkList(userId, lastUrlId, pageable);
        List<UserLinkResponse> userLinkResponses = linkResponses.stream()
                .map(link -> {
                    List<UrlHashTag> urlHashTagList = urlHashTagRepository.findByLinkId(link.getUrlId());
                    List<TagResponse> tagResponse = urlHashTagList.stream()
                            .map(urlHashTag -> new TagResponse(
                                    urlHashTag.getHashTag().getTag()))
                            .toList();

                    return new UserLinkResponse(
                            link.getUrlId(),
                            link.getLink(),
                            link.getTitle(),
                            link.getDescription(),
                            link.getThumbnail(),
                            link.getBookMarkCount(),
                            tagResponse
                    );
                }).collect(Collectors.toList());

        return new UserLinkListResponse(userLinkResponses);
    }

    private void checkUserId(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new BusinessException(ExceptionCodeConst.NOT_FOUND_USER));
    }

}