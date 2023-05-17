package project.linkarchive.backend.hashtag.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.BusinessException;
import project.linkarchive.backend.hashtag.domain.HashTag;
import project.linkarchive.backend.hashtag.repository.HashTagRepository;
import project.linkarchive.backend.hashtag.request.TagCreateRequest;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.domain.UserHashTag;
import project.linkarchive.backend.user.repository.UserHashTagRepository;
import project.linkarchive.backend.user.repository.UserRepository;

import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.ALREADY_EXIST_TAG;
import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.NOT_FOUND_USER;

@Service
@Transactional
public class HashTagApiService {

    private final UserRepository userRepository;
    private final HashTagRepository hashTagRepository;
    private final UserHashTagRepository userHashTagRepository;

    public HashTagApiService(UserRepository userRepository, HashTagRepository hashTagRepository, UserHashTagRepository userHashTagRepository) {
        this.userRepository = userRepository;
        this.hashTagRepository = hashTagRepository;
        this.userHashTagRepository = userHashTagRepository;
    }

    public void create(TagCreateRequest request, Long userId) {
        User user = findUserById(userId);
        HashTag hashTag = findAndBuildHashTagByTag(request);

        userHashTagRepository.findByHashTagId(hashTag.getId())
                .ifPresentOrElse(userHashTag -> {
                            throw new BusinessException(ALREADY_EXIST_TAG);
                        },
                        () -> {
                            UserHashTag getHashTag = UserHashTag.build(user, hashTag);
                            userHashTagRepository.save(getHashTag);
                        });
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));
    }

    private HashTag findAndBuildHashTagByTag(TagCreateRequest request) {
        return hashTagRepository.findByTag(request.getTag())
                .orElseGet(() -> HashTag.build(request));
    }


}