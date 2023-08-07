package project.linkarchive.backend.hashtag.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.custom.AlreadyExistException;
import project.linkarchive.backend.advice.exception.custom.LengthRequiredException;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.hashtag.domain.HashTag;
import project.linkarchive.backend.hashtag.domain.UserHashTag;
import project.linkarchive.backend.hashtag.repository.HashTagRepository;
import project.linkarchive.backend.hashtag.repository.UserHashTagRepository;
import project.linkarchive.backend.hashtag.request.CreateTagRequest;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.repository.UserRepository;

import static project.linkarchive.backend.advice.data.DataConstants.*;
import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.*;

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

    public void create(CreateTagRequest request, Long userId) {
        User user = findUserById(userId);
        validationTagLength(request);
        HashTag hashTag = findAndBuildHashTagByTag(request);

        userHashTagRepository.findByHashTagId(hashTag.getId())
                .ifPresentOrElse(userHashTag -> {
                            throw new AlreadyExistException(ALREADY_EXIST_TAG);
                        },
                        () -> {
                            UserHashTag getHashTag = UserHashTag.create(HASHTAG_DEFAULT_COUNT, user, hashTag);
                            userHashTagRepository.save(getHashTag);
                        });
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }

    private void validationTagLength(CreateTagRequest request) {
        boolean isTooLong = request.getTag().length() > MAXIMUM_TAG_LENGTH;
        boolean isTooShort = request.getTag().length() < MINIMUM_TAG_LENGTH;
        if (isTooLong || isTooShort) {
            throw new LengthRequiredException(LENGTH_REQUIRED_TAG);
        }
    }

    private HashTag findAndBuildHashTagByTag(CreateTagRequest request) {
        return hashTagRepository.findByTag(request.getTag())
                .orElseGet(() -> HashTag.create(request.getTag()));
    }

}