package project.linkarchive.backend.bookmark.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.BusinessException;
import project.linkarchive.backend.bookmark.domain.BookMark;
import project.linkarchive.backend.bookmark.repository.BookMarkRepository;
import project.linkarchive.backend.link.domain.Link;
import project.linkarchive.backend.link.repository.LinkRepository;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.UserRepository;

import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.*;

@Service
@Transactional
public class BookMarkApiService {

    private final UserRepository userRepository;
    private final LinkRepository linkRepository;
    private final BookMarkRepository bookMarkRepository;

    public BookMarkApiService(UserRepository userRepository, LinkRepository linkRepository, BookMarkRepository bookMarkRepository) {
        this.userRepository = userRepository;
        this.linkRepository = linkRepository;
        this.bookMarkRepository = bookMarkRepository;
    }

    //FIXME 동시성 이슈에 대한 해결이 필요해요.
    public void bookMark(Long urlId, Long userId) {
        Link link = getUrlValidation(urlId);
        existUrlValidation(link.getId());

        User user = findUserById(userId);
        BookMark bookMark = BookMark.of(user, link);
        bookMarkRepository.save(bookMark);

        linkRepository.increaseBookMarkCount(link.getId());
    }

    //FIXME 동시성 이슈에 대한 해결이 필요해요.
    public void bookMarkCancel(Long urlId, Long userId) {
        Link link = getUrlValidation(urlId);
        BookMark bookMark = findBookMarkByUrlAndUser(userId, link);
        bookMarkRepository.delete(bookMark);

        linkRepository.decreaseBookMarkCount(link.getId());
    }

    private Link getUrlValidation(Long urlId) {
        return linkRepository.findById(urlId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_LINK));
    }

    private void existUrlValidation(Long urlId) {
        if (bookMarkRepository.existsByLinkId(urlId)) {
            throw new BusinessException(ALREADY_EXIST_BOOKMARK);
        }
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));
    }

    private BookMark findBookMarkByUrlAndUser(Long userId, Link link) {
        return bookMarkRepository.findByLinkIdAndUserId(link.getId(), userId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_BOOKMARK));
    }

}