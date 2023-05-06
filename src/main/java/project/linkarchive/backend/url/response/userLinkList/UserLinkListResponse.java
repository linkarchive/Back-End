package project.linkarchive.backend.url.response.userLinkList;

import lombok.Getter;
import project.linkarchive.backend.user.response.userLinkList.UserTagList30Response;

import java.util.List;

@Getter
public class UserLinkListResponse {

    private List<UserTagList30Response> userTagList30ResponseList;
    private List<UserLinkTagListDetailResponse> userUrlLinkListDetailResponseList;

    public UserLinkListResponse(List<UserTagList30Response> userTagList30ResponseList, List<UserLinkTagListDetailResponse> userUrlLinkListDetailResponseList) {
        this.userTagList30ResponseList = userTagList30ResponseList;
        this.userUrlLinkListDetailResponseList = userUrlLinkListDetailResponseList;
    }

}