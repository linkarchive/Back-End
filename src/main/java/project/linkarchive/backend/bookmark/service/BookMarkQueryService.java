package project.linkarchive.backend.bookmark.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.BusinessException;
import project.linkarchive.backend.advice.exception.ExceptionCodeConst;
import project.linkarchive.backend.bookmark.repository.BookMarkRepositoryImpl;
import project.linkarchive.backend.link.domain.UrlHashTag;
import project.linkarchive.backend.link.repository.UrlHashTagRepository;
import project.linkarchive.backend.link.response.RefactorUserLinkList.LinkResponse;
import project.linkarchive.backend.link.response.RefactorUserLinkList.TagResponse;
import project.linkarchive.backend.link.response.RefactorUserLinkList.UserLinkListResponse;
import project.linkarchive.backend.link.response.RefactorUserLinkList.UserLinkResponse;
import project.linkarchive.backend.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BookMarkQueryService {

    private final UrlHashTagRepository urlHashTagRepository;
    private final UserRepository userRepository;
    private final BookMarkRepositoryImpl bookMarkRepositoryImpl;

    public BookMarkQueryService(UrlHashTagRepository urlHashTagRepository, UserRepository userRepository, BookMarkRepositoryImpl bookMarkRepositoryImpl) {
        this.urlHashTagRepository = urlHashTagRepository;
        this.userRepository = userRepository;
        this.bookMarkRepositoryImpl = bookMarkRepositoryImpl;
    }

    public UserLinkListResponse getMarkedLinkList(Long userId, Long lastUrlId, Pageable pageable) {

        userRepository.findById(userId).orElseThrow(() -> new BusinessException(ExceptionCodeConst.NOT_FOUND_USER));

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

}