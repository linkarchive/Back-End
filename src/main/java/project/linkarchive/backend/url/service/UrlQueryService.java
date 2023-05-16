package project.linkarchive.backend.url.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.bookmark.repository.BookMarkRepositoryImpl;
import project.linkarchive.backend.hashtag.response.TagListDetailResponse;
import project.linkarchive.backend.url.domain.UrlHashTag;
import project.linkarchive.backend.url.repository.UrlHashTagRepository;
import project.linkarchive.backend.url.repository.UrlRepositoryImpl;
import project.linkarchive.backend.url.response.linkList.UserExcludedLinkListDetailResponse;
import project.linkarchive.backend.url.response.linkList.UserExcludedLinkListResponse;
import project.linkarchive.backend.url.response.linkList.UserExcludedLinkTagListDetailResponse;
import project.linkarchive.backend.url.response.otherUserLinkList.*;
import project.linkarchive.backend.url.response.userLinkList.UserLinkListDetailResponse;
import project.linkarchive.backend.url.response.userLinkList.UserLinkListResponse;
import project.linkarchive.backend.url.response.userLinkList.UserLinkTagListDetailResponse;
import project.linkarchive.backend.url.response.userLinkList.UserTagListDetailResponse;
import project.linkarchive.backend.user.repository.UserHashTagRepositoryImpl;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UrlQueryService {

    private final UrlHashTagRepository urlHashTagRepository;
    private final UrlRepositoryImpl urlRepositoryImpl;
    private final UserHashTagRepositoryImpl userHashTagRepositoryImpl;

    private final BookMarkRepositoryImpl bookMarkRepositoryImpl;

    public UrlQueryService(UrlHashTagRepository urlHashTagRepository, UrlRepositoryImpl urlRepositoryImpl, UserHashTagRepositoryImpl userHashTagRepositoryImpl, BookMarkRepositoryImpl bookMarkRepositoryImpl) {

        this.urlHashTagRepository = urlHashTagRepository;
        this.urlRepositoryImpl = urlRepositoryImpl;
        this.userHashTagRepositoryImpl = userHashTagRepositoryImpl;
        this.bookMarkRepositoryImpl = bookMarkRepositoryImpl;

    }

    public UserLinkListResponse getUserLinkList(Pageable pageable, Long lastUrlId, Long userId) {
        List<TagListDetailResponse> userHashTagList = userHashTagRepositoryImpl.getTagListLimit30(userId);

        //FIXME 기능에 초점을 둬서 쿼리 성능이 좋지 않아요.
        List<UserLinkListDetailResponse> linkList = urlRepositoryImpl.getUserLinkList(pageable, lastUrlId, userId);
        List<UserLinkTagListDetailResponse> userLinkTagListDetailResponseList = linkList.stream()
                .map(url -> {
                    List<UrlHashTag> urlHashTagList = urlHashTagRepository.findByUrlId(url.getUrlId());
                    List<UserTagListDetailResponse> userTagListDetailResponseList = urlHashTagList.stream()
                            .map(urlHashTag -> new UserTagListDetailResponse(
                                    urlHashTag.getHashTag().getId(),
                                    urlHashTag.getHashTag().getTag()))
                            .collect(Collectors.toList());

                    return new UserLinkTagListDetailResponse(
                            url.getUrlId(),
                            url.getLink(),
                            url.getTitle(),
                            url.getDescription(),
                            url.getThumbnail(),
                            url.getBookMarkCount(),
                            userTagListDetailResponseList
                    );
                }).collect(Collectors.toList());

        return new UserLinkListResponse(userHashTagList, userLinkTagListDetailResponseList);
    }

    public UserExcludedLinkListResponse getLinkList(Pageable pageable, Long lastUrlId, Long userId) {
        //FIXME 기능에 초점을 둬서 쿼리 성능이 좋지 않아요.
        List<UserExcludedLinkListDetailResponse> linkList = urlRepositoryImpl.getLinkList(pageable, lastUrlId, userId);
        List<UserExcludedLinkTagListDetailResponse> linkDetailList = linkList.stream()
                .map(url -> {
                    List<UrlHashTag> urlHashTagList = urlHashTagRepository.findByUrlId(url.getUrlId());
                    List<TagListDetailResponse> tagListDetailResponseList = urlHashTagList.stream()
                            .map(urlHashTag -> TagListDetailResponse.of(urlHashTag))
                            .collect(Collectors.toList());
                    return UserExcludedLinkTagListDetailResponse.of(url, tagListDetailResponseList);
                }).collect(Collectors.toList());

        return new UserExcludedLinkListResponse(linkDetailList);
    }

    public OtherUserLinkListResponse getOtherLinkList(Long userId, Pageable pageable, Long lastUrlId) {
        List<TagListDetailResponse> userHashTagList = userHashTagRepositoryImpl.getTagListLimit30(userId);

        List<UrlResponse> urlResponses = urlRepositoryImpl.getOtherLinkList(userId,pageable,lastUrlId);
        List<UrlListResponse> urlListResponses = urlResponses.stream()
                .map(link -> {
                    List<UrlHashTag> urlHashTagList = urlHashTagRepository.findByUrlId(link.getUrlId());
                    List<UrlHashTagResponse> urlHashTagResponse = urlHashTagList.stream()
                            .map(urlHashTag -> new UrlHashTagResponse(
                                    urlHashTag.getHashTag().getTag()))
                            .collect(Collectors.toList());

                    return new UrlListResponse(
                            link.getUrlId(),
                            link.getLink(),
                            link.getTitle(),
                            link.getDescription(),
                            link.getThumbnail(),
                            link.getBookMarkCount(),
                            urlHashTagResponse
                    );
                }).collect(Collectors.toList());

        return new OtherUserLinkListResponse(userHashTagList, urlListResponses);
    }

    public LinkListResponse getMarkedLinkList(Long userId, Pageable pageable, Long lastUrlId) {

        List<UrlResponse> urlResponses = bookMarkRepositoryImpl.getMarkLinkList(userId,pageable,lastUrlId);
        List<UrlListResponse> urlListResponses = urlResponses.stream()
                .map(link -> {
                    List<UrlHashTag> urlHashTagList = urlHashTagRepository.findByUrlId(link.getUrlId());
                    List<UrlHashTagResponse> urlHashTagResponse = urlHashTagList.stream()
                            .map(urlHashTag -> new UrlHashTagResponse(
                                    urlHashTag.getHashTag().getTag()))
                            .toList();

                    return new UrlListResponse(
                            link.getUrlId(),
                            link.getLink(),
                            link.getTitle(),
                            link.getDescription(),
                            link.getThumbnail(),
                            link.getBookMarkCount(),
                            urlHashTagResponse
                    );
                }).collect(Collectors.toList());

        return new LinkListResponse(urlListResponses);
    }

}