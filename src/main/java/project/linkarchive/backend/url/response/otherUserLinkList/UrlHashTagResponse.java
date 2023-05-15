package project.linkarchive.backend.url.response.otherUserLinkList;

import lombok.Getter;

@Getter
public class UrlHashTagResponse {

    private String tagName;

    public UrlHashTagResponse(String tagName) {
        this.tagName = tagName;
    }

}
