package project.linkarchive.backend.bookmark.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.BusinessException;
import project.linkarchive.backend.advice.exception.ExceptionCodeConst;
import project.linkarchive.backend.bookmark.domain.BookMark;
import project.linkarchive.backend.bookmark.repository.BookMarkRepository;
import project.linkarchive.backend.bookmark.repository.BookMarkRepositoryImpl;
import project.linkarchive.backend.hashtag.response.TagListResponse;
import project.linkarchive.backend.hashtag.response.TagResponse;
import project.linkarchive.backend.link.domain.UrlHashTag;
import project.linkarchive.backend.link.repository.UrlHashTagRepository;
import project.linkarchive.backend.user.repository.UserRepository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BookMarkQueryService {

    public static final Long MAX_SIZE = 30L;

    private final UserRepository userRepository;
    private final BookMarkRepository bookMarkRepository;
    private final UrlHashTagRepository urlHashTagRepository;
    private final BookMarkRepositoryImpl bookMarkRepositoryImpl;

    public BookMarkQueryService(UserRepository userRepository, BookMarkRepository bookMarkRepository, UrlHashTagRepository urlHashTagRepository, BookMarkRepositoryImpl bookMarkRepositoryImpl) {
        this.userRepository = userRepository;
        this.bookMarkRepository = bookMarkRepository;
        this.urlHashTagRepository = urlHashTagRepository;
        this.bookMarkRepositoryImpl = bookMarkRepositoryImpl;
    }

    public TagListResponse getMarkTagList(Long userId) {
        checkUserId(userId);

        List<BookMark> bookMarkList = bookMarkRepository.findByUserId(userId);

        Map<String, Integer> tagCountMap = new HashMap<>();
        bookMarkList.forEach(bookMark -> {
            List<UrlHashTag> urlHashTagList = urlHashTagRepository.findByLinkId(bookMark.getLink().getId());
            urlHashTagList.forEach(urlHashTag -> {
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
        checkUserId(userId);
        checkSize(size);
        List<BookMark> bookMarkList = bookMarkRepository.findByUserId(userId);

        Map<String, Integer> tagCountMap = new HashMap<>();
        bookMarkList.forEach(bookMark -> {
            List<UrlHashTag> urlHashTagList = urlHashTagRepository.findByLinkId(bookMark.getLink().getId());
            urlHashTagList.forEach(urlHashTag -> {
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

    private void checkUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ExceptionCodeConst.NOT_FOUND_USER));
    }

    private void checkSize(Long size) {
        if (size > MAX_SIZE) {
            throw new BusinessException(ExceptionCodeConst.OVER_SIZE_TAG);
        }
    }

}