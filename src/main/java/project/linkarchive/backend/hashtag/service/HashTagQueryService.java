package project.linkarchive.backend.hashtag.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.hashtag.response.TagListResponse;
import project.linkarchive.backend.link.response.UserLinkList.TagResponse;
import project.linkarchive.backend.user.repository.UserHashTagRepositoryImpl;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class HashTagQueryService {

    private UserHashTagRepositoryImpl userHashTagRepositoryImpl;

    public HashTagQueryService(UserHashTagRepositoryImpl userHashTagRepositoryImpl) {
        this.userHashTagRepositoryImpl = userHashTagRepositoryImpl;
    }

    public TagListResponse getUserTagList(Long userId) {
        List<TagResponse> tagList = userHashTagRepositoryImpl.getUserTagList(userId);
        return new TagListResponse(tagList);
    }

}