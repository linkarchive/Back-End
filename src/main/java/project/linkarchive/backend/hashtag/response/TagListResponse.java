package project.linkarchive.backend.hashtag.response;

import lombok.Getter;

import java.util.List;

@Getter
public class TagListResponse {

    private List<TagResponse> tagList;

    public TagListResponse(List<TagResponse> tagList) {
        this.tagList = tagList;
    }

}