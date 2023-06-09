package project.linkarchive.backend.bookmark.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.custom.AlreadyExistException;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
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

    public void bookMark(Long linkId, Long userId) {
        Link link = findLinkById(linkId);
        User user = findUserById(userId);
        existUrlValidation(link.getId(), user.getId());

        BookMark bookMark = BookMark.build(user, link);
        bookMarkRepository.save(bookMark);

        linkRepository.increaseBookMarkCount(link.getId());
    }

    public void bookMarkCancel(Long linkId, Long userId) {
        Link link = findLinkById(linkId);
        BookMark bookMark = findBookMarkByLinkAndUser(link, userId);
        bookMarkRepository.delete(bookMark);

        linkRepository.decreaseBookMarkCount(link.getId());
    }

    private Link findLinkById(Long linkId) {
        return linkRepository.findById(linkId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_LINK));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }

    private void existUrlValidation(Long linkId, Long userId) {
        if (bookMarkRepository.existsByLinkIdAndUserId(linkId, userId)) {
            throw new AlreadyExistException(ALREADY_EXIST_BOOKMARK);
        }
    }

    private BookMark findBookMarkByLinkAndUser(Link link, Long userId) {
        return bookMarkRepository.findByLinkIdAndUserId(link.getId(), userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_BOOKMARK));
    }

}