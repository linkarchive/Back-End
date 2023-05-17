package project.linkarchive.backend.hashtag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.BusinessException;
import project.linkarchive.backend.advice.exception.ExceptionCodeConst;
import project.linkarchive.backend.hashtag.response.TagListDetailResponse;
import project.linkarchive.backend.hashtag.response.TagListResponse;
import project.linkarchive.backend.hashtag.response.TagResponse;
import project.linkarchive.backend.hashtag.response.UserTagListResponse;
import project.linkarchive.backend.user.repository.UserHashTagRepositoryImpl;
import project.linkarchive.backend.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HashTagQueryService {

    private final UserHashTagRepositoryImpl userHashTagRepositoryImpl;
    private final UserRepository userRepository;
    public static final Long MAX_SIZE = 30L;

    public UserTagListResponse getLoginUserTagList(Long userId) {
        List<TagListDetailResponse> tagList = userHashTagRepositoryImpl.getUserTagList(userId);
        return new UserTagListResponse(tagList);
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
            throw new BusinessException(ExceptionCodeConst.OVER_SIZE);
        }
    }


}