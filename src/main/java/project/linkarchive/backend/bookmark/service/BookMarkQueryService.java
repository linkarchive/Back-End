package project.linkarchive.backend.bookmark.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.BusinessException;
import project.linkarchive.backend.advice.exception.ExceptionCodeConst;
import project.linkarchive.backend.bookmark.repository.BookMarkRepositoryImpl;
import project.linkarchive.backend.url.domain.UrlHashTag;
import project.linkarchive.backend.url.repository.UrlHashTagRepository;
import project.linkarchive.backend.url.response.RefactorUserLinkList.LinkResponse;
import project.linkarchive.backend.url.response.RefactorUserLinkList.TagResponse;
import project.linkarchive.backend.url.response.RefactorUserLinkList.UserLinkListResponse;
import project.linkarchive.backend.url.response.RefactorUserLinkList.UserLinkResponse;
import project.linkarchive.backend.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookMarkQueryService {

    private final UrlHashTagRepository urlHashTagRepository;
    private final UserRepository userRepository;
    private final BookMarkRepositoryImpl bookMarkRepositoryImpl;

    public UserLinkListResponse getMarkedLinkList(Long userId, Pageable pageable, Long lastUrlId) {

        userRepository.findById(userId).orElseThrow(()-> new BusinessException(ExceptionCodeConst.NOT_FOUND_USER));

        List<LinkResponse> linkResponses = bookMarkRepositoryImpl.getMarkLinkList(userId,pageable,lastUrlId);
        List<UserLinkResponse> userLinkResponses = linkResponses.stream()
                .map(link -> {
                    List<UrlHashTag> urlHashTagList = urlHashTagRepository.findByUrlId(link.getUrlId());
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
}