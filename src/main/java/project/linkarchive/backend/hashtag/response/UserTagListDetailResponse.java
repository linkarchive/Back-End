package project.linkarchive.backend.hashtag.response;

import lombok.Getter;

@Getter
public class UserTagListDetailResponse {

    private Long tagId;
    private String tagName;

    public UserTagListDetailResponse(Long tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }

}