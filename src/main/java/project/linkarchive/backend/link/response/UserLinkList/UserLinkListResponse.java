package project.linkarchive.backend.link.response.UserLinkList;

import lombok.Getter;

import java.util.List;

@Getter
public class UserLinkListResponse {

    private List<UserLinkResponse> linkList;

    public UserLinkListResponse(List<UserLinkResponse> linkList) {
        this.linkList = linkList;
    }

}
