package project.linkarchive.backend.url.response.linkList;

import lombok.Getter;

@Getter
public class UserExcludedTagListDetailResponse {

    private Long tagId;
    private String tagName;

    public UserExcludedTagListDetailResponse(Long tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }

}