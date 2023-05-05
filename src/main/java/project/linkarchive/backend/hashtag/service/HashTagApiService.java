package project.linkarchive.backend.hashtag.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.BusinessException;
import project.linkarchive.backend.hashtag.domain.HashTag;
import project.linkarchive.backend.hashtag.repository.HashTagRepository;
import project.linkarchive.backend.hashtag.request.TagCreateRequest;
import project.linkarchive.backend.user.domain.UserHashTag;
import project.linkarchive.backend.user.repository.UserHashTagRepository;

import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.ALREADY_EXIST_TAG;

@Service
@Transactional
public class HashTagApiService {

    private final HashTagRepository hashTagRepository;
    private final UserHashTagRepository userHashTagRepository;

    public HashTagApiService(HashTagRepository hashTagRepository, UserHashTagRepository userHashTagRepository) {
        this.hashTagRepository = hashTagRepository;
        this.userHashTagRepository = userHashTagRepository;
    }

    public void create(TagCreateRequest request) {
        HashTag hashTag = hashTagRepository.findByTag(request.getTag())
                .orElseGet(() -> HashTag.builder()
                        .tag(request.getTag())
                        .build());

        userHashTagRepository.findByHashTagId(hashTag.getId())
                .ifPresentOrElse(userHashTag -> {
                    throw new BusinessException(ALREADY_EXIST_TAG);
                }, () -> {
                    UserHashTag getHashTag = UserHashTag.builder()
                            .userHashTagCount(0L)
                            .hashTag(hashTag)
                            .build();
                    userHashTagRepository.save(getHashTag);
                });
    }

}