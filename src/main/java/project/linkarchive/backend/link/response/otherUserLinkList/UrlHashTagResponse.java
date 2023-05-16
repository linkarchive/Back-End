package project.linkarchive.backend.link.response.otherUserLinkList;

import lombok.Getter;

@Getter
public class UrlHashTagResponse {

    private String tagName;

    public UrlHashTagResponse(String tagName) {
        this.tagName = tagName;
    }

}
