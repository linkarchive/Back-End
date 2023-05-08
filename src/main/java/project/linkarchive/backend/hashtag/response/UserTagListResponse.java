package project.linkarchive.backend.hashtag.response;

import lombok.Getter;

import java.util.List;

@Getter
public class UserTagListResponse {

    private List<UserTagListDetailResponse> userTagListDetailResponseList;

    public UserTagListResponse(List<UserTagListDetailResponse> userTagListDetailResponseList) {
        this.userTagListDetailResponseList = userTagListDetailResponseList;
    }

}