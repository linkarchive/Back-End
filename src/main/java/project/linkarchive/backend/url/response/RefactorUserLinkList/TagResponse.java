package project.linkarchive.backend.url.response.RefactorUserLinkList;

import lombok.Getter;

@Getter
public class TagResponse {

    private String tagName;

    public TagResponse(String tagName) {
        this.tagName = tagName;
    }

}
