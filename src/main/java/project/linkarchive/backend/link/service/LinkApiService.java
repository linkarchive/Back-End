package project.linkarchive.backend.link.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.custom.*;
import project.linkarchive.backend.bookmark.repository.BookMarkRepository;
import project.linkarchive.backend.hashtag.domain.Hashtag;
import project.linkarchive.backend.hashtag.repository.HashTagRepository;
import project.linkarchive.backend.hashtag.repository.UserHashTagRepository;
import project.linkarchive.backend.link.domain.Link;
import project.linkarchive.backend.link.domain.LinkHashtag;
import project.linkarchive.backend.link.enums.LinkStatus;
import project.linkarchive.backend.link.repository.LinkHashTagRepository;
import project.linkarchive.backend.link.repository.LinkRepository;
import project.linkarchive.backend.link.request.CreateLinkRequest;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.UserRepository;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static project.linkarchive.backend.advice.data.DataConstants.*;
import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.*;

@Service
@Transactional
public class LinkApiService {

    private final UserRepository userRepository;
    private final HashTagRepository hashTagRepository;
    private final LinkHashTagRepository linkHashTagRepository;
    private final UserHashTagRepository userHashTagRepository;
    private final LinkRepository linkRepository;
    private final BookMarkRepository bookMarkRepository;

    public LinkApiService(
            UserRepository userRepository,
            HashTagRepository hashTagRepository,
            LinkHashTagRepository linkHashTagRepository,
            UserHashTagRepository userHashTagRepository,
            LinkRepository linkRepository,
            BookMarkRepository bookMarkRepository
    ) {
        this.userRepository = userRepository;
        this.hashTagRepository = hashTagRepository;
        this.linkHashTagRepository = linkHashTagRepository;
        this.userHashTagRepository = userHashTagRepository;
        this.linkRepository = linkRepository;
        this.bookMarkRepository = bookMarkRepository;
    }

    public void create(CreateLinkRequest request, Long userId) {
        validateLinkTitleLength(request);
        Set<String> tagsFromRequest = extractTagsFromRequest(request);
        checkExceededTagCount(tagsFromRequest);

        User user = getUserById(userId);

        Link link = Link.create(request, user);
        addTagsToLinkAndIncrementUserTagCount(tagsFromRequest, link);

        linkRepository.save(link);
        userRepository.increaseLinkCount(userId);
    }

    public void delete(Long linkId, Long userId) {
        validateUserExists(userId);

        Link link = getLinkByLinkIdAndUserId(linkId, userId);
        validateLinkNotInTrash(link);

        bookMarkRepository.findByLinkId(linkId)
                .forEach(bookMarkRepository::delete);

        link.delete();
        linkRepository.resetBookmarkCount(link.getId());
        userRepository.decreaseLinkCount(userId);
    }

    public void restore(Long linkId, Long userId) {
        Link link = getLinkByLinkIdAndUserId(linkId, userId);

        validateUserAccessToLink(userId, link);
        validateLinkInTrash(link);

        link.restore();
        userRepository.increaseLinkCount(userId);
    }

    private void validateLinkTitleLength(CreateLinkRequest request) {
        if (request.getTitle() == null || request.getTitle().length() < MINIMUM_TITLE_LENGTH) {
            throw new LengthRequiredException(LENGTH_REQUIRED_TITLE);
        }
    }

    private void validateUserExists(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }

    private Link getLinkByLinkIdAndUserId(Long linkId, Long userId) {
        return linkRepository.findByIdAndUserId(linkId, userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_LINK));
    }

    private Set<String> extractTagsFromRequest(CreateLinkRequest request) {
        return request.getTagList().stream()
                .peek(this::validateTagLength)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private void validateTagLength(String tag) {
        boolean isTooLong = tag.length() > MAXIMUM_TAG_LENGTH;
        boolean isTooShort = tag.length() < MINIMUM_TAG_LENGTH;
        if (isTooLong || isTooShort) {
            throw new LengthRequiredException(LENGTH_REQUIRED_TAG);
        }
    }

    private void checkExceededTagCount(Set<String> requestForTag) {
        if (requestForTag.size() > TAG_SIZE) {
            throw new ExceededException(EXCEEDED_TAG_LIMIT_10);
        }
    }

    private void addTagsToLinkAndIncrementUserTagCount(Set<String> tagsFromRequest, Link link) {
        tagsFromRequest.stream()
                .map(tag -> hashTagRepository.findByTag(tag)
                        .orElseGet(() -> Hashtag.create(tag)))
                .forEach(hashTag -> {
                    userHashTagRepository.findByHashtagId(hashTag.getId())
                            .ifPresent(tag -> userHashTagRepository.increaseUsageCount(tag.getId()));

                    linkHashTagRepository.save(LinkHashtag.create(link, hashTag));
                });
    }

    private void validateLinkNotInTrash(Link link) {
        if (link.getLinkStatus().equals(LinkStatus.TRASH)) {
            throw new AlreadyExistException(ALREADY_TRASH_LINK);
        }
    }

    private void validateUserAccessToLink(Long userId, Link link) {
        if (!link.getUser().getId().equals(userId)) {
            throw new UnauthorizedException(INVALID_LINK_ACCESS);
        }
    }

    private void validateLinkInTrash(Link link) {
        if (!link.getLinkStatus().equals(LinkStatus.TRASH)) {
            throw new InvalidException(INVALID_TRASH_LINK);
        }
    }

}