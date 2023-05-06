package project.linkarchive.backend.url.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.url.domain.UrlHashTag;
import project.linkarchive.backend.url.repository.UrlHashTagRepository;
import project.linkarchive.backend.url.repository.UrlRepositoryImpl;
import project.linkarchive.backend.url.response.userLinkList.UserLinkTagListResponse;
import project.linkarchive.backend.url.response.userLinkList.UserUrlLinkListDetailResponse;
import project.linkarchive.backend.url.response.userLinkList.UserUrlLinkTagListDetailResponse;
import project.linkarchive.backend.url.response.userLinkList.UserUrlTagListDetailResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UrlQueryService {

    private final UrlRepositoryImpl urlRepositoryImpl;
    private final UrlHashTagRepository urlHashTagRepository;

    public UrlQueryService(UrlRepositoryImpl urlRepositoryImpl, UrlHashTagRepository urlHashTagRepository) {
        this.urlRepositoryImpl = urlRepositoryImpl;
        this.urlHashTagRepository = urlHashTagRepository;
    }

    public UserLinkTagListResponse getUserLinkList(Pageable pageable, Long lastUrlId) {
        List<UserUrlLinkListDetailResponse> userUrlLinkListDetailResponseList = urlRepositoryImpl.getUserLinkList(pageable, lastUrlId);
        List<UserUrlLinkTagListDetailResponse> content = userUrlLinkListDetailResponseList.stream()
                .map(u -> {
                    List<UrlHashTag> urlHashTagList = urlHashTagRepository.findByUrlId(u.getUrlId());
                    List<UserUrlTagListDetailResponse> userUrlTagListDetailResponseList = urlHashTagList.stream()
                            .map(h -> new UserUrlTagListDetailResponse(
                                    h.getHashTag().getId(),
                                    h.getHashTag().getTag()))
                            .collect(Collectors.toList());

                    return new UserUrlLinkTagListDetailResponse(
                            u.getUrlId(),
                            u.getLink(),
                            u.getTitle(),
                            u.getDescription(),
                            u.getThumbnail(),
                            u.getBookMarkCount(),
                            userUrlTagListDetailResponseList
                    );
                }).collect(Collectors.toList());


        return new UserLinkTagListResponse(content);
    }

}