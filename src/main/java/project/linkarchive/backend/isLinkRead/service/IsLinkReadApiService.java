package project.linkarchive.backend.isLinkRead.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.custom.AlreadyExistException;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.isLinkRead.domain.IsLinkRead;
import project.linkarchive.backend.isLinkRead.repository.IsLinkReadRepository;
import project.linkarchive.backend.link.domain.Link;
import project.linkarchive.backend.link.repository.LinkRepository;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.UserRepository;

import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.*;

@Service
@Transactional
public class IsLinkReadApiService {

    private final UserRepository userRepository;
    private final LinkRepository linkRepository;
    private final IsLinkReadRepository isLinkReadRepository;

    public IsLinkReadApiService(UserRepository userRepository, LinkRepository linkRepository, IsLinkReadRepository isLinkReadRepository) {
        this.userRepository = userRepository;
        this.linkRepository = linkRepository;
        this.isLinkReadRepository = isLinkReadRepository;
    }

    public void isLinkRead(Long linkId, Long userId) {
        User user = getUserById(userId);
        Link link = getLinkById(linkId);
        existIsLinkReadValidation(user, link);

        IsLinkRead isLinkRead = IsLinkRead.build(user, link);
        isLinkReadRepository.save(isLinkRead);
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }

    private Link getLinkById(Long linkId) {
        return linkRepository.findById(linkId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_LINK));
    }

    private void existIsLinkReadValidation(User user, Link link) {
        if (isLinkReadRepository.existsByLinkIdAndUserId(link.getId(), user.getId())) {
            throw new AlreadyExistException(ALREADY_EXIST_IS_LINK_READ);
        }
    }

}