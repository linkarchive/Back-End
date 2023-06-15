package project.linkarchive.backend.link.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.custom.ExceededException;
import project.linkarchive.backend.advice.exception.custom.LengthRequiredException;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.hashtag.domain.HashTag;
import project.linkarchive.backend.hashtag.repository.HashTagRepository;
import project.linkarchive.backend.hashtag.repository.UserHashTagRepository;
import project.linkarchive.backend.link.domain.Link;
import project.linkarchive.backend.link.domain.LinkHashTag;
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

    public LinkApiService(UserRepository userRepository, HashTagRepository hashTagRepository, LinkHashTagRepository linkHashTagRepository, UserHashTagRepository userHashTagRepository,
                          LinkRepository linkRepository) {
        this.userRepository = userRepository;
        this.hashTagRepository = hashTagRepository;
        this.linkHashTagRepository = linkHashTagRepository;
        this.userHashTagRepository = userHashTagRepository;
        this.linkRepository = linkRepository;
    }

    public void create(CreateLinkRequest request, Long userId) {
        validationTitleLength(request);

        User user = findUserById(userId);
        Set<String> tagsFromRequest = getTagsFromRequest(request);
        exceededTagCount(tagsFromRequest);

        Link link = Link.build(request, user);
        addTagsToLinkAndIncrementUserTagCount(tagsFromRequest, link);

        linkRepository.save(link);
    }

    private void validationTitleLength(CreateLinkRequest request) {
        if (request.getTitle() == null || request.getTitle().length() < MINIMUM_TITLE_LENGTH) {
            throw new LengthRequiredException(LENGTH_REQUIRED_TITLE);
        }
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }

    private Set<String> getTagsFromRequest(CreateLinkRequest request) {
        return request.getTags().stream()
                .peek(tag -> validationTagLength(tag))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private void validationTagLength(String tag) {
        boolean isTooLong = tag.length() > MAXIMUM_TAG_LENGTH;
        boolean isTooShort = tag.length() < MINIMUM_TAG_LENGTH;
        if (isTooLong || isTooShort) {
            throw new LengthRequiredException(LENGTH_REQUIRED_TAG);
        }
    }

    private void exceededTagCount(Set<String> requestForTag) {
        if (requestForTag.size() > MAX_TAG_COUNT) {
            throw new ExceededException(EXCEEDED_TAG_LIMIT_10);
        }
    }

    private void addTagsToLinkAndIncrementUserTagCount(Set<String> tagsFromRequest, Link link) {
        tagsFromRequest.stream()
                .map(tag -> hashTagRepository.findByTag(tag)
                        .orElseGet(() -> HashTag.build(tag)))
                .forEach(hashTag -> {
                    userHashTagRepository.findByHashTagId(hashTag.getId())
                            .ifPresent(tag -> userHashTagRepository.increaseUsageCount(tag.getId()));
                    linkHashTagRepository.save(LinkHashTag.build(link, hashTag));
                });
    }

}