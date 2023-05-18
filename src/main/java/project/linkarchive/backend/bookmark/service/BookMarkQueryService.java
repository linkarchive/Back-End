package project.linkarchive.backend.bookmark.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.bookmark.domain.BookMark;
import project.linkarchive.backend.bookmark.repository.BookMarkRepository;
import project.linkarchive.backend.bookmark.repository.BookMarkRepositoryImpl;
import project.linkarchive.backend.hashtag.response.TagResponse;
import project.linkarchive.backend.link.domain.UrlHashTag;
import project.linkarchive.backend.link.repository.UrlHashTagRepository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BookMarkQueryService {

    private final BookMarkRepository bookMarkRepository;
    private final UrlHashTagRepository urlHashTagRepository;
    private final BookMarkRepositoryImpl bookMarkRepositoryImpl;

    public BookMarkQueryService(BookMarkRepository bookMarkRepository, UrlHashTagRepository urlHashTagRepository, BookMarkRepositoryImpl bookMarkRepositoryImpl) {
        this.bookMarkRepository = bookMarkRepository;
        this.urlHashTagRepository = urlHashTagRepository;
        this.bookMarkRepositoryImpl = bookMarkRepositoryImpl;
    }

    public List<TagResponse> getMarkTagList(Long userId) {
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

        return tagList;
    }

}