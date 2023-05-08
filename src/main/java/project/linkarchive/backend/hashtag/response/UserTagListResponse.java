package project.linkarchive.backend.hashtag.response;

import lombok.Getter;

import java.util.List;

@Getter
public class UserTagListResponse {

    private List<UserTagListDetailResponse> tagList;

    public UserTagListResponse(List<UserTagListDetailResponse> tagList) {
        this.tagList = tagList;
    }

}