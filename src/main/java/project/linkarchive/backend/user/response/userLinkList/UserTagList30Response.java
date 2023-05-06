package project.linkarchive.backend.user.response.userLinkList;

import lombok.Getter;

@Getter
public class UserTagList30Response {

    private Long tagIg;
    private String tagName;

    public UserTagList30Response(Long tagIg, String tagName) {
        this.tagIg = tagIg;
        this.tagName = tagName;
    }

}