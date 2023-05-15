package project.linkarchive.backend.hashtag.response;

import lombok.Getter;

import java.util.List;

@Getter
public class UserTagListResponse {

    private List<TagListDetailResponse> tagList;

    public UserTagListResponse(List<TagListDetailResponse> tagList) {
        this.tagList = tagList;
    }

}