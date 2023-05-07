package project.linkarchive.backend.url.response.linkList;

import lombok.Getter;

@Getter
public class TagListDetailResponse {

    private Long tagId;
    private String tagName;

    public TagListDetailResponse(Long tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }

}