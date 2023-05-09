package project.linkarchive.backend.bookmark.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.BusinessException;
import project.linkarchive.backend.bookmark.domain.BookMark;
import project.linkarchive.backend.bookmark.repository.BookMarkRepository;
import project.linkarchive.backend.url.domain.Url;
import project.linkarchive.backend.url.repository.UrlRepository;

import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.ALREADY_EXIST_BOOKMARK;
import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.NOT_FOUND_URL;

@Service
@Transactional
public class BookMarkApiService {

    private final UrlRepository urlRepository;
    private final BookMarkRepository bookMarkRepository;

    public BookMarkApiService(UrlRepository urlRepository, BookMarkRepository bookMarkRepository) {
        this.urlRepository = urlRepository;
        this.bookMarkRepository = bookMarkRepository;
    }

    //FIXME 동시성 이슈에 대한 해결이 필요해요.
    public void bookMark(Long urlId) {
        Url url = urlRepository.findById(urlId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_URL));

        if (bookMarkRepository.existsByUrl(url)) {
            throw new BusinessException(ALREADY_EXIST_BOOKMARK);
        }

        BookMark bookMark = BookMark.builder()
                .url(url)
                .build();
        bookMarkRepository.save(bookMark);

        url.increaseBookMarkCount(url.getBookMarkCount());
    }

    //FIXME 동시성 이슈에 대한 해결이 필요해요.


}