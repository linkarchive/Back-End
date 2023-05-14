package project.linkarchive.backend.url.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.url.domain.Url;
import project.linkarchive.backend.hashtag.response.TagListDetailResponse;
import project.linkarchive.backend.url.domain.UrlHashTag;
import project.linkarchive.backend.url.repository.UrlHashTagRepository;
import project.linkarchive.backend.url.repository.UrlRepository;
import project.linkarchive.backend.url.repository.UrlRepositoryImpl;
import project.linkarchive.backend.url.response.linkList.UserExcludedLinkListDetailResponse;
import project.linkarchive.backend.url.response.linkList.UserExcludedLinkListResponse;
import project.linkarchive.backend.url.response.linkList.UserExcludedLinkTagListDetailResponse;
import project.linkarchive.backend.url.response.linkList.UserExcludedTagListDetailResponse;
import project.linkarchive.backend.url.response.otherUserLinkList.OtherUserHashtagListResponse;
import project.linkarchive.backend.url.response.otherUserLinkList.OtherUserLinkListResponse;
import project.linkarchive.backend.url.response.otherUserLinkList.OtherUserUrlListResponse;
import project.linkarchive.backend.url.response.userLinkList.UserLinkListDetailResponse;
import project.linkarchive.backend.url.response.userLinkList.UserLinkListResponse;
import project.linkarchive.backend.url.response.userLinkList.UserLinkTagListDetailResponse;
import project.linkarchive.backend.url.response.userLinkList.UserTagListDetailResponse;
import project.linkarchive.backend.user.domain.UserHashTag;
import project.linkarchive.backend.user.repository.UserHashTagRepository;
import project.linkarchive.backend.user.repository.UserHashTagRepositoryImpl;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UrlQueryService {

    private final UserHashTagRepository userHashTagRepository;
    private final UrlHashTagRepository urlHashTagRepository;
    private final UrlRepository urlRepository;
    private final UrlRepositoryImpl urlRepositoryImpl;
    private final UserHashTagRepositoryImpl userHashTagRepositoryImpl;

    public UrlQueryService(UserHashTagRepository userHashTagRepository, UrlHashTagRepository urlHashTagRepository, UrlRepositoryImpl urlRepositoryImpl, UserHashTagRepositoryImpl userHashTagRepositoryImpl, UrlRepository urlRepository) {

        this.userHashTagRepository = userHashTagRepository;
        this.urlHashTagRepository = urlHashTagRepository;
        this.urlRepository = urlRepository;
        this.urlRepositoryImpl = urlRepositoryImpl;
        this.userHashTagRepositoryImpl = userHashTagRepositoryImpl;

    }

    public UserLinkListResponse getUserLinkList(Pageable pageable, Long lastUrlId, Long userId) {
        List<TagListDetailResponse> userHashTagList = userHashTagRepositoryImpl.getUserHashTagList(userId);

        //FIXME 기능에 초점을 둬서 쿼리 성능이 좋지 않아요.
        List<UserLinkListDetailResponse> userUrlLinkListDetailResponseList = urlRepositoryImpl.getUserLinkList(pageable, lastUrlId);
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

    public OtherUserLinkListResponse getOtherUserLinkList(Long userId) {
        List<Url> url = urlRepository.getByUserId(userId);
        List<UserHashTag> userHashTags = userHashTagRepository.getByUserId(userId);

        return responseOtherUserLinkList(url, userHashTags);
    }

    private OtherUserLinkListResponse responseOtherUserLinkList(List<Url> urlList, List<UserHashTag> userHashTags) {
        responseUrlList(urlList);
        responseUserHashtagList(userHashTags);

        OtherUserLinkListResponse response = new OtherUserLinkListResponse(responseUrlList(urlList), responseUserHashtagList(userHashTags));

        return response;

    }

    private List<OtherUserUrlListResponse> responseUrlList(List<Url> urlList) {
        return urlList.stream().map(OtherUserUrlListResponse::new).toList();
    }

    private List<OtherUserHashtagListResponse> responseUserHashtagList(List<UserHashTag> userHashTags) {
        return userHashTags.stream().map(OtherUserHashtagListResponse::new).toList();
    }

}