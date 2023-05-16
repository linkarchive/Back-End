package project.linkarchive.backend.link.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.hashtag.response.TagListDetailResponse;
import project.linkarchive.backend.link.domain.UrlHashTag;
import project.linkarchive.backend.link.repository.UrlHashTagRepository;
import project.linkarchive.backend.link.repository.UrlRepositoryImpl;
import project.linkarchive.backend.link.response.RefactorUserLinkList.LinkResponse;
import project.linkarchive.backend.link.response.RefactorUserLinkList.TagResponse;
import project.linkarchive.backend.link.response.RefactorUserLinkList.UserLinkListResponse;
import project.linkarchive.backend.link.response.RefactorUserLinkList.UserLinkResponse;
import project.linkarchive.backend.link.response.linkList.UserExcludedLinkListDetailResponse;
import project.linkarchive.backend.link.response.linkList.UserExcludedLinkListResponse;
import project.linkarchive.backend.link.response.linkList.UserExcludedLinkTagListDetailResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class LinkQueryService {

    private final UrlHashTagRepository urlHashTagRepository;
    private final UrlRepositoryImpl urlRepositoryImpl;

    public LinkQueryService(UrlHashTagRepository urlHashTagRepository, UrlRepositoryImpl urlRepositoryImpl) {
        this.urlHashTagRepository = urlHashTagRepository;
        this.urlRepositoryImpl = urlRepositoryImpl;
    }

    public UserLinkListResponse getUserLinkList(Long userId, Pageable pageable, Long lastUrlId) {

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

}