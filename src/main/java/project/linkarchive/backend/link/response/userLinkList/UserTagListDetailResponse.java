package project.linkarchive.backend.link.response.userLinkList;

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