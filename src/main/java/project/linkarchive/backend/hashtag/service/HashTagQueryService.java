package project.linkarchive.backend.hashtag.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.hashtag.domain.HashTag;
import project.linkarchive.backend.hashtag.repository.HashTagRepository;
import project.linkarchive.backend.hashtag.repository.UserHashTagRepositoryImpl;
import project.linkarchive.backend.hashtag.response.TagListResponse;
import project.linkarchive.backend.hashtag.response.TagResponse;
import project.linkarchive.backend.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.NOT_FOUND_USER;

@Service
@Transactional(readOnly = true)
public class HashTagQueryService {

    private final UserRepository userRepository;
    private final UserHashTagRepositoryImpl userHashTagRepositoryImpl;
    private final HashTagRepository hashTagRepository;

    public HashTagQueryService(UserRepository userRepository, UserHashTagRepositoryImpl userHashTagRepositoryImpl, HashTagRepository hashTagRepository) {
        this.userRepository = userRepository;
        this.userHashTagRepositoryImpl = userHashTagRepositoryImpl;
        this.hashTagRepository = hashTagRepository;
    }

    public TagListResponse getUserTagList(Long userId) {
        getUserById(userId);

        List<TagResponse> tagList = userHashTagRepositoryImpl.getUserTagList(userId);

        return new TagListResponse(tagList);
    }

    public TagListResponse getUserTagList10(Long userId) {
        getUserById(userId);

        List<TagResponse> tagResponses = userHashTagRepositoryImpl.getUserTagList10(userId);

        return new TagListResponse(tagResponses);
    }

    public TagListResponse getArchiveTagList() {
        List<HashTag> tagList = hashTagRepository.getArchiveTagList();

        List<TagResponse> tagResponses = tagList.stream()
                .map(TagResponse::create)
                .collect(Collectors.toList());

        return new TagListResponse(tagResponses);
    }

    private void getUserById(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }

}