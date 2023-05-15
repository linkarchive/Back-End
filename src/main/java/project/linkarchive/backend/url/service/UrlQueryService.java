package project.linkarchive.backend.url.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.hashtag.response.TagListDetailResponse;
import project.linkarchive.backend.url.domain.UrlHashTag;
import project.linkarchive.backend.url.repository.UrlHashTagRepository;
import project.linkarchive.backend.url.repository.UrlRepositoryImpl;
import project.linkarchive.backend.url.response.linkList.UserExcludedLinkListDetailResponse;
import project.linkarchive.backend.url.response.linkList.UserExcludedLinkListResponse;
import project.linkarchive.backend.url.response.linkList.UserExcludedLinkTagListDetailResponse;
import project.linkarchive.backend.url.response.linkList.UserExcludedTagListDetailResponse;
import project.linkarchive.backend.url.response.otherUserLinkList.OtherUserLinkListResponse;
import project.linkarchive.backend.url.response.otherUserLinkList.UrlHashTagResponse;
import project.linkarchive.backend.url.response.otherUserLinkList.UrlListResponse;
import project.linkarchive.backend.url.response.otherUserLinkList.UrlResponse;
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

    public UrlQueryService(UrlHashTagRepository urlHashTagRepository, UrlRepositoryImpl urlRepositoryImpl, UserHashTagRepositoryImpl userHashTagRepositoryImpl) {

        this.urlHashTagRepository = urlHashTagRepository;
        this.urlRepositoryImpl = urlRepositoryImpl;
        this.userHashTagRepositoryImpl = userHashTagRepositoryImpl;

    }

    public UserLinkListResponse getUserLinkList(Pageable pageable, Long lastUrlId, Long userId) {
        List<TagListDetailResponse> userHashTagList = userHashTagRepositoryImpl.getUserHashTagList(userId);

        //FIXME 기능에 초점을 둬서 쿼리 성능이 좋지 않아요.
        List<UserLinkListDetailResponse> userUrlLinkListDetailResponseList = urlRepositoryImpl.getUserLinkList(pageable, lastUrlId, userId);
        List<UserLinkTagListDetailResponse> userLinkTagListDetailResponseList = userUrlLinkListDetailResponseList.stream()
                .map(link -> {
                    List<UrlHashTag> urlHashTagList = urlHashTagRepository.findByUrlId(link.getUrlId());
                    List<UserTagListDetailResponse> userTagListDetailResponseList = urlHashTagList.stream()
                            .map(urlHashTag -> new UserTagListDetailResponse(
                                    urlHashTag.getHashTag().getId(),
                                    urlHashTag.getHashTag().getTag()))
                            .collect(Collectors.toList());

                    return new UserLinkTagListDetailResponse(
                            link.getUrlId(),
                            link.getLink(),
                            link.getTitle(),
                            link.getDescription(),
                            link.getThumbnail(),
                            link.getBookMarkCount(),
                            userTagListDetailResponseList
                    );
                }).collect(Collectors.toList());

        return new UserLinkListResponse(userHashTagList, userLinkTagListDetailResponseList);
    }

    public UserExcludedLinkListResponse getLinkList(Pageable pageable, Long lastUrlId) {
        //FIXME 기능에 초점을 둬서 쿼리 성능이 좋지 않아요.
        //FIXME 로그인한 유저의 리스트를 제외하고 조회해야 해요.
        List<UserExcludedLinkListDetailResponse> userExcludedLinkListDetailResponseList = urlRepositoryImpl.getLinkList(pageable, lastUrlId);
        List<UserExcludedLinkTagListDetailResponse> userExcludedLinkTagListDetailResponseList = userExcludedLinkListDetailResponseList.stream()
                .map(l -> {
                    List<UrlHashTag> urlHashTagList = urlHashTagRepository.findByUrlId(l.getUrlId());
                    List<UserExcludedTagListDetailResponse> userExcludedTagListDetailResponseList = urlHashTagList.stream()
                            .map(h -> new UserExcludedTagListDetailResponse(
                                    h.getHashTag().getId(),
                                    h.getHashTag().getTag()))
                            .collect(Collectors.toList());
                    return new UserExcludedLinkTagListDetailResponse(
                            l.getUserId(),
                            l.getName(),
                            l.getProfileImage(),
                            l.getUrlId(),
                            l.getLink(),
                            l.getTitle(),
                            l.getDescription(),
                            l.getThumbnail(),
                            l.getBookMarkCount(),
                            userExcludedTagListDetailResponseList
                    );
                }).collect(Collectors.toList());

        return new UserExcludedLinkListResponse(userExcludedLinkTagListDetailResponseList);
    }

    public OtherUserLinkListResponse getOtherLinkList(long userId, long size, Long lastUrlId) {
        List<TagListDetailResponse> userHashTagList = userHashTagRepositoryImpl.getUserHashTagList(userId);

        List<UrlResponse> urlResponses = urlRepositoryImpl.getLinkList(userId,size,lastUrlId);
        List<UrlListResponse> urlListResponses = urlResponses.stream()
                .map(link -> {
                    List<UrlHashTag> urlHashTagList = urlHashTagRepository.findByUrlId(link.getUrlId());
                    List<UrlHashTagResponse> urlHashTagResponse = urlHashTagList.stream()
                            .map(urlHashTag -> new UrlHashTagResponse(
                                    urlHashTag.getHashTag().getId(),
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
}