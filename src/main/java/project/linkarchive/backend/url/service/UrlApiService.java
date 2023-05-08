package project.linkarchive.backend.url.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.hashtag.domain.HashTag;
import project.linkarchive.backend.hashtag.repository.HashTagRepository;
import project.linkarchive.backend.url.domain.Url;
import project.linkarchive.backend.url.domain.UrlHashTag;
import project.linkarchive.backend.url.repository.UrlHashTagRepository;
import project.linkarchive.backend.url.request.UrlCreateRequest;
import project.linkarchive.backend.user.repository.UserHashTagRepository;

@Service
@Transactional
public class UrlApiService {

    private final HashTagRepository hashTagRepository;
    private final UrlHashTagRepository urlHashTagRepository;
    private final UserHashTagRepository userHashTagRepository;

    public UrlApiService(HashTagRepository hashTagRepository, UrlHashTagRepository urlHashTagRepository, UserHashTagRepository userHashTagRepository) {
        this.hashTagRepository = hashTagRepository;
        this.urlHashTagRepository = urlHashTagRepository;
        this.userHashTagRepository = userHashTagRepository;
    }

    public void create(UrlCreateRequest urlCreateRequest) {
        Url url = Url.of(urlCreateRequest);
        urlCreateRequest.getTag().stream()
                .map(hashtag -> hashTagRepository.findByTag(hashtag)
                        .orElseGet(() -> HashTag.builder()
                                .tag(hashtag)
                                .build()))
                .forEach(hashTag -> {
                    userHashTagRepository.findByHashTagId(hashTag.getId())
                            .ifPresent(tag -> tag.increaseUserHashTagCount(tag.getUserHashTagCount()));
                    urlHashTagRepository.save(UrlHashTag.builder()
                            .url(url)
                            .hashTag(hashTag)
                            .build());
                });
    }

}