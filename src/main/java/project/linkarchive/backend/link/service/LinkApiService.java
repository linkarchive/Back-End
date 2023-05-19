package project.linkarchive.backend.link.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.ExceededException;
import project.linkarchive.backend.advice.exception.NotFoundException;
import project.linkarchive.backend.hashtag.domain.HashTag;
import project.linkarchive.backend.hashtag.repository.HashTagRepository;
import project.linkarchive.backend.link.domain.Link;
import project.linkarchive.backend.link.domain.UrlHashTag;
import project.linkarchive.backend.link.repository.UrlHashTagRepository;
import project.linkarchive.backend.link.request.CreateLinkRequest;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.UserHashTagRepository;
import project.linkarchive.backend.user.repository.UserRepository;

import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.EXCEEDED_TAG_LIMIT_10;
import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.NOT_FOUND_USER;

@Service
@Transactional
public class LinkApiService {

    private final UserRepository userRepository;
    private final HashTagRepository hashTagRepository;
    private final UrlHashTagRepository urlHashTagRepository;
    private final UserHashTagRepository userHashTagRepository;

    public LinkApiService(UserRepository userRepository, HashTagRepository hashTagRepository, UrlHashTagRepository urlHashTagRepository, UserHashTagRepository userHashTagRepository) {
        this.userRepository = userRepository;
        this.hashTagRepository = hashTagRepository;
        this.urlHashTagRepository = urlHashTagRepository;
        this.userHashTagRepository = userHashTagRepository;
    }

    public void create(CreateLinkRequest request, Long userId) {
        User user = findUserById(userId);
        exceededTagLimit(request);

        Link link = Link.of(request, user);
        request.getTag().stream()
                .map(tag -> hashTagRepository.findByTag(tag)
                        .orElseGet(() -> HashTag.build(tag))
                )
                .forEach(hashTag -> {
                    userHashTagRepository.findByHashTagId(hashTag.getId())
                            .ifPresent(tag -> tag.increaseUserHashTagCount(tag.getUsageCount()));
                    urlHashTagRepository.save(UrlHashTag.of(link, hashTag));
                });
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }

    private void exceededTagLimit(CreateLinkRequest request) {
        if (request.getTag().size() > 10) {
            throw new ExceededException(EXCEEDED_TAG_LIMIT_10);
        }
    }

}