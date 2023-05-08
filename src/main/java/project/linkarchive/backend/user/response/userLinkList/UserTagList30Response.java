package project.linkarchive.backend.user.response.userLinkList;

import lombok.Getter;

@Getter
public class UserTagList30Response {

    private Long tagId;
    private String tagName;

    public UserTagList30Response(Long tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }

}