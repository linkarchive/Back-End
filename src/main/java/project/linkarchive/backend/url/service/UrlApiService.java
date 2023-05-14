package project.linkarchive.backend.url.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.BusinessException;
import project.linkarchive.backend.hashtag.domain.HashTag;
import project.linkarchive.backend.hashtag.repository.HashTagRepository;
import project.linkarchive.backend.url.domain.Url;
import project.linkarchive.backend.url.domain.UrlHashTag;
import project.linkarchive.backend.url.repository.UrlHashTagRepository;
import project.linkarchive.backend.url.request.UrlCreateRequest;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.UserHashTagRepository;
import project.linkarchive.backend.user.repository.UserRepository;

import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.NOT_FOUND_USER;

@Service
@Transactional
public class UrlApiService {

    private final UserRepository userRepository;
    private final HashTagRepository hashTagRepository;
    private final UrlHashTagRepository urlHashTagRepository;
    private final UserHashTagRepository userHashTagRepository;

    public UrlApiService(UserRepository userRepository, HashTagRepository hashTagRepository, UrlHashTagRepository urlHashTagRepository, UserHashTagRepository userHashTagRepository) {
        this.userRepository = userRepository;
        this.hashTagRepository = hashTagRepository;
        this.urlHashTagRepository = urlHashTagRepository;
        this.userHashTagRepository = userHashTagRepository;
    }

    public void create(UrlCreateRequest urlCreateRequest, Long userId) {
        User user = findUserById(userId);

        Url url = Url.of(urlCreateRequest, user);
        urlCreateRequest.getTag().stream()
                .map(hashtag -> hashTagRepository.findByTag(hashtag)
                        .orElseGet(() -> HashTag.builder()
                                .tag(hashtag)
                                .build())
                )
                .forEach(hashTag -> {
                    userHashTagRepository.findByHashTagId(hashTag.getId())
                            .ifPresent(tag -> tag.increaseUserHashTagCount(tag.getUserHashTagCount()));
                    urlHashTagRepository.save(UrlHashTag.builder()
                            .url(url)
                            .hashTag(hashTag)
                            .build());
                });
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));
    }

}