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
        List<UserLinkListDetailResponse> linkList = urlRepositoryImpl.getUserLinkList(pageable, lastUrlId);
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
        List<UserExcludedLinkTagListDetailResponse> userExcludedLinkTagListDetailResponseList = linkList.stream()
                .map(url -> {
                    List<UrlHashTag> urlHashTagList = urlHashTagRepository.findByUrlId(url.getUrlId());
                    List<TagListDetailResponse> tagListDetailResponseList = urlHashTagList.stream()
                            .map(h -> TagListDetailResponse.of(h.getHashTag().getTag()))
                            .collect(Collectors.toList());
                    return UserExcludedLinkTagListDetailResponse.of(url, tagListDetailResponseList);
                }).collect(Collectors.toList());

        return new UserExcludedLinkListResponse(userExcludedLinkTagListDetailResponseList);
    }

}