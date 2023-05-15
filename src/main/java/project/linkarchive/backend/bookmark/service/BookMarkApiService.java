package project.linkarchive.backend.bookmark.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.BusinessException;
import project.linkarchive.backend.bookmark.domain.BookMark;
import project.linkarchive.backend.bookmark.repository.BookMarkRepository;
import project.linkarchive.backend.url.domain.Url;
import project.linkarchive.backend.url.repository.UrlRepository;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.UserRepository;

import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.*;

@Service
@Transactional
public class BookMarkApiService {

    private final UserRepository userRepository;
    private final UrlRepository urlRepository;
    private final BookMarkRepository bookMarkRepository;

    public BookMarkApiService(UserRepository userRepository, UrlRepository urlRepository, BookMarkRepository bookMarkRepository) {
        this.userRepository = userRepository;
        this.urlRepository = urlRepository;
        this.bookMarkRepository = bookMarkRepository;
    }

    //FIXME 동시성 이슈에 대한 해결이 필요해요.
    public void bookMark(Long urlId, Long userId) {
        Url url = getUrlValidation(urlId);
        existUrlValidation(url.getId());

        User user = findUserById(userId);
        BookMark bookMark = BookMark.of(user, url);
        bookMarkRepository.save(bookMark);

        bookMarkRepository.increaseBookMarkCount(url.getId());
    }

    //FIXME 동시성 이슈에 대한 해결이 필요해요.
    public void bookMarkCancel(Long urlId, Long userId) {
        Url url = getUrlValidation(urlId);
        BookMark bookMark = findBookMarkByUrlAndUser(userId, url);
        bookMarkRepository.delete(bookMark);

        bookMarkRepository.decreaseBookMarkCount(url.getId());
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

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));
    }

    private BookMark findBookMarkByUrlAndUser(Long userId, Url url) {
        return bookMarkRepository.findByUrlIdAndUserId(url.getId(), userId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_BOOKMARK));
    }

}