package project.linkarchive.backend.url.response.userLinkList;

import lombok.Getter;

import java.util.List;

@Getter
public class UserLinkTagListResponse {

    private List<UserUrlLinkTagListDetailResponse> userUrlLinkListDetailResponseList;

    public UserLinkTagListResponse(List<UserUrlLinkTagListDetailResponse> userUrlLinkListDetailResponseList) {
        this.userUrlLinkListDetailResponseList = userUrlLinkListDetailResponseList;
    }

}