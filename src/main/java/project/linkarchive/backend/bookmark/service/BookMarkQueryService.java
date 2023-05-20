package project.linkarchive.backend.bookmark.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.custom.ExceededException;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.bookmark.domain.BookMark;
import project.linkarchive.backend.bookmark.repository.BookMarkRepository;
import project.linkarchive.backend.bookmark.repository.BookMarkRepositoryImpl;
import project.linkarchive.backend.hashtag.response.TagListResponse;
import project.linkarchive.backend.hashtag.response.TagResponse;
import project.linkarchive.backend.link.domain.LinkHashTag;
import project.linkarchive.backend.link.repository.LinkHashTagRepository;
import project.linkarchive.backend.link.response.linkList.LinkResponse;
import project.linkarchive.backend.link.response.linkList.UserLinkListResponse;
import project.linkarchive.backend.link.response.linkList.UserLinkResponse;
import project.linkarchive.backend.user.repository.UserRepository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.EXCEEDED_TAG_SIZE;
import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.NOT_FOUND_USER;

@Service
@Transactional(readOnly = true)
public class BookMarkQueryService {

    public static final Long MAX_SIZE = 30L;

    private final UserRepository userRepository;
    private final BookMarkRepository bookMarkRepository;
    private final LinkHashTagRepository linkHashTagRepository;
    private final BookMarkRepositoryImpl bookMarkRepositoryImpl;

    public BookMarkQueryService(UserRepository userRepository, BookMarkRepository bookMarkRepository, LinkHashTagRepository linkHashTagRepository, BookMarkRepositoryImpl bookMarkRepositoryImpl) {
        this.userRepository = userRepository;
        this.bookMarkRepository = bookMarkRepository;
        this.linkHashTagRepository = linkHashTagRepository;
        this.bookMarkRepositoryImpl = bookMarkRepositoryImpl;
    }

    public UserLinkListResponse getMarkedLinkList(Long userId, Long lastLinkId, Pageable pageable) {
        validateUserExists(userId);

        List<LinkResponse> linkResponseList = bookMarkRepositoryImpl.getMarkLinkList(userId, lastLinkId, pageable);

        boolean hasNext = isHasNextLinkList(pageable, linkResponseList);

        List<UserLinkResponse> userLinkResponseList = linkResponseList.stream()
                .map(linkResponse -> {
                    List<LinkHashTag> linkHashTagList = linkHashTagRepository.findByLinkId(linkResponse.getLinkId());
                    List<TagResponse> tagList = linkHashTagList.stream()
                            .map(TagResponse::build)
                            .collect(Collectors.toList());

                    return UserLinkResponse.build(linkResponse, tagList);
                }).collect(Collectors.toList());

        return new UserLinkListResponse(userLinkResponseList, hasNext);
    }

    public TagListResponse getMarkTagList(Long userId) {
        validateUserExists(userId);

        List<BookMark> bookMarkList = bookMarkRepository.findByUserId(userId);

        Map<String, Integer> tagCountMap = new HashMap<>();
        bookMarkList.forEach(bookMark -> {
            List<LinkHashTag> linkHashTagList = linkHashTagRepository.findByLinkId(bookMark.getLink().getId());
            linkHashTagList.forEach(urlHashTag -> {
                String tag = urlHashTag.getHashTag().getTag();
                tagCountMap.put(tag, tagCountMap.getOrDefault(tag, 0) + 1);
            });
        });

        List<TagResponse> tagList = tagCountMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(entry -> new TagResponse(entry.getKey()))
                .collect(Collectors.toList());

        return new TagListResponse(tagList);
    }

    public TagListResponse getMarkTagLimitList(Long userId, Long size) {
        validateUserExists(userId);
        validateSizeDoesNotExceedMax(size);
        List<BookMark> bookMarkList = bookMarkRepository.findByUserId(userId);

        Map<String, Integer> tagCountMap = new HashMap<>();
        bookMarkList.forEach(bookMark -> {
            List<LinkHashTag> linkHashTagList = linkHashTagRepository.findByLinkId(bookMark.getLink().getId());
            linkHashTagList.forEach(urlHashTag -> {
                String tag = urlHashTag.getHashTag().getTag();
                tagCountMap.put(tag, tagCountMap.getOrDefault(tag, 0) + 1);
            });
        });

        List<TagResponse> tagList = tagCountMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(size)
                .map(entry -> new TagResponse(entry.getKey()))
                .collect(Collectors.toList());

        return new TagListResponse(tagList);
    }

    private void validateUserExists(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }

    private void validateSizeDoesNotExceedMax(Long size) {
        if (size > MAX_SIZE) {
            throw new ExceededException(EXCEEDED_TAG_SIZE);
        }
    }

    private boolean isHasNextLinkList(Pageable pageable, List<LinkResponse> linkResponseList) {
        boolean hasNext = false;
        if (linkResponseList.size() > pageable.getPageSize()) {
            linkResponseList.remove(linkResponseList.size() - 1);
            hasNext = true;
        }
        return hasNext;
    }

}