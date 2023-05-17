package project.linkarchive.backend.hashtag.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.BusinessException;
import project.linkarchive.backend.advice.exception.ExceptionCodeConst;
import project.linkarchive.backend.hashtag.response.TagListResponse;
import project.linkarchive.backend.hashtag.response.TagResponse;
import project.linkarchive.backend.user.repository.UserHashTagRepositoryImpl;
import project.linkarchive.backend.user.repository.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class HashTagQueryService {

    public static final Long MAX_SIZE = 30L;

    private final UserRepository userRepository;
    private final UserHashTagRepositoryImpl userHashTagRepositoryImpl;

    public HashTagQueryService(UserRepository userRepository, UserHashTagRepositoryImpl userHashTagRepositoryImpl) {
        this.userRepository = userRepository;
        this.userHashTagRepositoryImpl = userHashTagRepositoryImpl;
    }

    public TagListResponse getUserTagList(Long userId) {
        List<TagResponse> tagList = userHashTagRepositoryImpl.getUserTagList(userId);
        return new TagListResponse(tagList);
    }

    // 사용자 별 자주 사용하는 해시태그 N개 조회 007
    public TagListResponse getTagList(Long userId, Long size) {
        checkUserId(userId);
        checkSize(size);

        List<TagResponse> tagResponses = userHashTagRepositoryImpl.getTagListLimitSize(userId, size);
        return new TagListResponse(tagResponses);
    }

    private void checkUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ExceptionCodeConst.NOT_FOUND_USER));
    }

    private void checkSize(Long size) {
        if (size > MAX_SIZE) {
            throw new BusinessException(ExceptionCodeConst.OVER_SIZE_TAG);
        }
    }

}