package project.linkarchive.backend.hashtag.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.hashtag.response.TagListDetailResponse;
import project.linkarchive.backend.hashtag.response.UserTagListResponse;
import project.linkarchive.backend.user.repository.UserHashTagRepositoryImpl;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class HashTagQueryService {

    private UserHashTagRepositoryImpl userHashTagRepositoryImpl;

    public HashTagQueryService(UserHashTagRepositoryImpl userHashTagRepositoryImpl) {
        this.userHashTagRepositoryImpl = userHashTagRepositoryImpl;
    }

    public UserTagListResponse getLoginUserTagList(Long userId) {
        List<TagListDetailResponse> tagList = userHashTagRepositoryImpl.getUserTagList(userId);
        return new UserTagListResponse(tagList);
    }

}