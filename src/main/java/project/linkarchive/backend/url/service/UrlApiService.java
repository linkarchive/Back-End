package project.linkarchive.backend.url.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.hashtag.domain.HashTag;
import project.linkarchive.backend.hashtag.repository.HashTagRepository;
import project.linkarchive.backend.url.domain.Url;
import project.linkarchive.backend.url.domain.UrlHashTag;
import project.linkarchive.backend.url.repository.UrlHashTagRepository;
import project.linkarchive.backend.url.repository.UrlRepository;
import project.linkarchive.backend.url.request.UrlCreateRequest;

@Service
@Transactional
public class UrlApiService {

    private final UrlRepository urlRepository;
    private final HashTagRepository hashTagRepository;
    private final UrlHashTagRepository urlHashTagRepository;

    public UrlApiService(UrlRepository urlRepository, HashTagRepository hashTagRepository, UrlHashTagRepository urlHashTagRepository) {
        this.urlRepository = urlRepository;
        this.hashTagRepository = hashTagRepository;
        this.urlHashTagRepository = urlHashTagRepository;
    }

    public void create(UrlCreateRequest urlCreateRequest) {
        Url url = Url.of(urlCreateRequest);
        urlCreateRequest.getTag().stream()
                .map(hashtag -> {
                    HashTag hashTag = hashTagRepository.findByTag(hashtag)
                            .orElseGet(() -> HashTag.builder().tag(hashtag).build());
                    return UrlHashTag.builder().url(url).hashTag(hashTag).build();
                })
                .forEach(urlHashTagRepository::save);
    }

}