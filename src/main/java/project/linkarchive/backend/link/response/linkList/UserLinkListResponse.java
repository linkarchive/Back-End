package project.linkarchive.backend.link.response.linkList;

import lombok.Getter;

import java.util.List;

@Getter
public class UserLinkListResponse {

    private List<UserLinkResponse> linkList;
    private Boolean hasNext;

    public UserLinkListResponse(List<UserLinkResponse> linkList, Boolean hasNext) {
        this.linkList = linkList;
        this.hasNext = hasNext;
    }

}