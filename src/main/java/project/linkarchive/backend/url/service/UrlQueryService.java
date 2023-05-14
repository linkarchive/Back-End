package project.linkarchive.backend.url.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.url.domain.UrlHashTag;
import project.linkarchive.backend.url.repository.UrlHashTagRepository;
import project.linkarchive.backend.url.repository.UrlRepositoryImpl;
import project.linkarchive.backend.url.response.linkList.UserExcludedLinkListDetailResponse;
import project.linkarchive.backend.url.response.linkList.UserExcludedLinkListResponse;
import project.linkarchive.backend.url.response.linkList.UserExcludedLinkTagListDetailResponse;
import project.linkarchive.backend.url.response.linkList.UserExcludedTagListDetailResponse;
import project.linkarchive.backend.url.response.userLinkList.UserLinkListDetailResponse;
import project.linkarchive.backend.url.response.userLinkList.UserLinkListResponse;
import project.linkarchive.backend.url.response.userLinkList.UserLinkTagListDetailResponse;
import project.linkarchive.backend.url.response.userLinkList.UserTagListDetailResponse;
import project.linkarchive.backend.user.domain.UserHashTag;
import project.linkarchive.backend.user.repository.UserHashTagRepository;
import project.linkarchive.backend.user.response.UserTagList30Response;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UrlQueryService {

    private final UserHashTagRepository userHashTagRepository;
    private final UrlRepositoryImpl urlRepositoryImpl;
    private final UrlHashTagRepository urlHashTagRepository;

    public UrlQueryService(UserHashTagRepository userHashTagRepository, UrlRepositoryImpl urlRepositoryImpl, UrlHashTagRepository urlHashTagRepository) {
        this.userHashTagRepository = userHashTagRepository;
        this.urlRepositoryImpl = urlRepositoryImpl;
        this.urlHashTagRepository = urlHashTagRepository;
    }

    public UserLinkListResponse getUserLinkList(Pageable pageable, Long lastUrlId) {
        //FIXME user가 자주 사용하는 해시태그 30개 조회로 리팩토링 필요. user 정보 없어서 조회하는걸로 대체했어요
        List<UserHashTag> userHashTagList = userHashTagRepository.findAll();
        List<UserTagList30Response> userTagList30ResponseList = userHashTagList.stream()
                .map(h -> new UserTagList30Response(h.getHashTag().getId(), h.getHashTag().getTag())).collect(Collectors.toList());

        //FIXME 기능에 초점을 둬서 쿼리 성능이 좋지 않아요.
        List<UserLinkListDetailResponse> userUrlLinkListDetailResponseList = urlRepositoryImpl.getUserLinkList(pageable, lastUrlId);
        List<UserLinkTagListDetailResponse> userLinkTagListDetailResponseList = userUrlLinkListDetailResponseList.stream()
                .map(l -> {
                    List<UrlHashTag> urlHashTagList = urlHashTagRepository.findByUrlId(l.getUrlId());
                    List<UserTagListDetailResponse> userTagListDetailResponseList = urlHashTagList.stream()
                            .map(h -> new UserTagListDetailResponse(
                                    h.getHashTag().getId(),
                                    h.getHashTag().getTag()))
                            .collect(Collectors.toList());

                    return new UserLinkTagListDetailResponse(
                            l.getUrlId(),
                            l.getLink(),
                            l.getTitle(),
                            l.getDescription(),
                            l.getThumbnail(),
                            l.getBookMarkCount(),
                            userTagListDetailResponseList
                    );
                }).collect(Collectors.toList());

        return new UserLinkListResponse(userTagList30ResponseList, userLinkTagListDetailResponseList);
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

}