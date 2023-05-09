package project.linkarchive.backend.bookmark.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.BusinessException;
import project.linkarchive.backend.bookmark.domain.BookMark;
import project.linkarchive.backend.bookmark.repository.BookMarkRepository;
import project.linkarchive.backend.url.domain.Url;
import project.linkarchive.backend.url.repository.UrlRepository;

import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.*;

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
        Url url = getUrlValidation(urlId);
        existUrlValidation(url.getId());

        BookMark bookMark = BookMark.builder()
                .url(url)
                .build();
        bookMarkRepository.save(bookMark);

        url.increaseBookMarkCount(url.getBookMarkCount());
    }

    //FIXME 동시성 이슈에 대한 해결이 필요해요.
    public void bookMarkCancel(Long urlId) {
        Url url = getUrlValidation(urlId);

        BookMark bookMark = bookMarkRepository.findByUrlId(url.getId())
                .orElseThrow(() -> new BusinessException(NOT_FOUND_BOOKMARK));
        bookMarkRepository.delete(bookMark);

        url.decreaseBookMarkCount(url.getBookMarkCount());
    }

    private Url getUrlValidation(Long urlId) {
        return urlRepository.findById(urlId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_URL));
    }

    private void existUrlValidation(Long urlId) {
        if (bookMarkRepository.existsByUrlId(urlId)) {
            throw new BusinessException(ALREADY_EXIST_BOOKMARK);
        }
    }

}