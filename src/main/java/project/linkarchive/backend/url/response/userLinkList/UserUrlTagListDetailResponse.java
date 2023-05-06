package project.linkarchive.backend.url.response.userLinkList;

import lombok.Getter;

@Getter
public class UserUrlTagListDetailResponse {

    private Long tagId;
    private String tagName;

    public UserUrlTagListDetailResponse(Long tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }

}