package project.linkarchive.backend.hashtag.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.custom.ExceededException;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.hashtag.repository.UserHashTagRepositoryImpl;
import project.linkarchive.backend.hashtag.response.TagListResponse;
import project.linkarchive.backend.hashtag.response.TagResponse;
import project.linkarchive.backend.user.repository.UserRepository;

import java.util.List;

import static project.linkarchive.backend.advice.data.Constants.MAX_SIZE;
import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.EXCEEDED_TAG_SIZE;
import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.NOT_FOUND_USER;

@Service
@Transactional(readOnly = true)
public class HashTagQueryService {

    private final UserRepository userRepository;
    private final UserHashTagRepositoryImpl userHashTagRepositoryImpl;

    public HashTagQueryService(UserRepository userRepository, UserHashTagRepositoryImpl userHashTagRepositoryImpl) {
        this.userRepository = userRepository;
        this.userHashTagRepositoryImpl = userHashTagRepositoryImpl;
    }

    public TagListResponse getUserTagList(Long userId) {
        checkUserId(userId);

        List<TagResponse> tagList = userHashTagRepositoryImpl.getUserTagList(userId);

        return new TagListResponse(tagList);
    }

    public TagListResponse getLimitedTagList(Long userId, int size) {
        checkUserId(userId);
        checkSize(size);

        List<TagResponse> tagResponses = userHashTagRepositoryImpl.getLimitedTagList(userId, size);

        return new TagListResponse(tagResponses);
    }

    private void checkUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }

    private void checkSize(int size) {
        if (size > MAX_SIZE) {
            throw new ExceededException(EXCEEDED_TAG_SIZE);
        }
    }

}