package project.linkarchive.backend.url.response.userLinkList;

import lombok.Getter;
import project.linkarchive.backend.user.response.userLinkList.UserTagList30Response;

import java.util.List;

@Getter
public class UserLinkListResponse {

    private List<UserTagList30Response> userTagList;
    private List<UserLinkTagListDetailResponse> userLinkList;

    public UserLinkListResponse(List<UserTagList30Response> userTagList, List<UserLinkTagListDetailResponse> userLinkList) {
        this.userTagList = userTagList;
        this.userLinkList = userLinkList;
    }

}