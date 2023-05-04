package project.linkarchive.backend.url.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.url.domain.Url;
import project.linkarchive.backend.url.repository.UrlRepository;
import project.linkarchive.backend.url.request.UrlCreateRequest;

@Service
@Transactional
public class UrlApiService {

    private final UrlRepository urlRepository;

    public UrlApiService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public void create(UrlCreateRequest urlCreateRequest) {
        Url url = Url.of(urlCreateRequest);
        urlRepository.save(url);
    }

}