package project.linkarchive.backend.hashtag.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.linkarchive.backend.hashtag.response.UserTagListDetailResponse;
import project.linkarchive.backend.hashtag.response.UserTagListResponse;
import project.linkarchive.backend.user.domain.UserHashTag;
import project.linkarchive.backend.user.repository.UserHashTagRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class HashTagQueryService {

    private UserHashTagRepository userHashTagRepository;

    public HashTagQueryService(UserHashTagRepository userHashTagRepository) {
        this.userHashTagRepository = userHashTagRepository;
    }

    //FIXME 유저 정보에 관한 기능이 생기면 로그인 유저 기준으로 조회할거에요.
    public UserTagListResponse getUserTagList() {
        List<UserHashTag> content = userHashTagRepository.findAll();
        List<UserTagListDetailResponse> userTagListDetailResponseList = content.stream()
                .map(h -> new UserTagListDetailResponse(
                        h.getHashTag().getId(),
                        h.getHashTag().getTag()))
                .collect(Collectors.toList());
        return new UserTagListResponse(userTagListDetailResponseList);
    }

}