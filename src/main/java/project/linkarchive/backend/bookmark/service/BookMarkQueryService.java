package project.linkarchive.backend.bookmark.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.bookmark.repository.BookMarkRepositoryImpl;
import project.linkarchive.backend.bookmark.response.UserMarkedLinkListDetailResponse;
import project.linkarchive.backend.bookmark.response.UserMarkedLinkListResponse;
import project.linkarchive.backend.bookmark.response.UserMarkedLinkTagListDetailResponse;
import project.linkarchive.backend.hashtag.repository.HashTagRepository;
import project.linkarchive.backend.hashtag.response.TagListDetailResponse;
import project.linkarchive.backend.url.domain.UrlHashTag;
import project.linkarchive.backend.url.repository.UrlHashTagRepository;
import project.linkarchive.backend.user.domain.UserHashTag;
import project.linkarchive.backend.user.repository.UserHashTagRepository;
import project.linkarchive.backend.user.response.UserTagList30Response;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BookMarkQueryService {

    private final HashTagRepository hashTagRepository;
    private final UserHashTagRepository userHashTagRepository;
    private final BookMarkRepositoryImpl bookMarkRepositoryImpl;
    private final UrlHashTagRepository urlHashTagRepository;

    public BookMarkQueryService(HashTagRepository hashTagRepository, UserHashTagRepository userHashTagRepository, BookMarkRepositoryImpl bookMarkRepositoryImpl, UrlHashTagRepository urlHashTagRepository) {
        this.hashTagRepository = hashTagRepository;
        this.userHashTagRepository = userHashTagRepository;
        this.bookMarkRepositoryImpl = bookMarkRepositoryImpl;
        this.urlHashTagRepository = urlHashTagRepository;
    }

    public UserMarkedLinkListResponse getMarkLinkList(Pageable pageable, Long lastUrlId) {
        //FIXME user가 자주 사용하는 해시태그 30개 조회로 리팩토링 필요. user 정보 없어서 조회하는걸로 대체했어요
        List<UserHashTag> userHashTagList = userHashTagRepository.findAll();
        List<UserTagList30Response> userTagList30ResponseList = userHashTagList.stream()
                .map(h -> new UserTagList30Response(h.getHashTag().getId(), h.getHashTag().getTag())).collect(Collectors.toList());

        //FIXME 기능에 초점을 둬서 쿼리 성능이 좋지 않아요.
        List<UserMarkedLinkListDetailResponse> userMarkedLinkListDetailResponseList = bookMarkRepositoryImpl.getMarkLinkList(pageable, lastUrlId);
        List<UserMarkedLinkTagListDetailResponse> userMarkedLinkTagListDetailResponseList = userMarkedLinkListDetailResponseList.stream()
                .map(m -> {
                    List<UrlHashTag> urlHashTagList = urlHashTagRepository.findByUrlId(m.getUrlId());
                    List<TagListDetailResponse> tagListResponseListDetailList = urlHashTagList.stream()
                            .map(h -> new TagListDetailResponse(
                                    h.getHashTag().getId(),
                                    h.getHashTag().getTag())).collect(Collectors.toList());

                    return new UserMarkedLinkTagListDetailResponse(
                            m.getMarkId(),
                            m.getUrlId(),
                            m.getLink(),
                            m.getTitle(),
                            m.getDescription(),
                            m.getThumbnail(),
                            m.getMarkCount(),
                            tagListResponseListDetailList
                    );
                }).collect(Collectors.toList());

        return new UserMarkedLinkListResponse(userTagList30ResponseList, userMarkedLinkTagListDetailResponseList);
    }

}