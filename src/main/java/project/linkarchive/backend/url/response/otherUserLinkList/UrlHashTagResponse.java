package project.linkarchive.backend.url.response.otherUserLinkList;

import lombok.Getter;

@Getter
public class UrlHashTagResponse {

    private Long tagId;
    private String tagName;

    public UrlHashTagResponse(Long tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }

}
