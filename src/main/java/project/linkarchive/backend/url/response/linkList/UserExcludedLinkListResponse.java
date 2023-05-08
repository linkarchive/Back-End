package project.linkarchive.backend.url.response.linkList;

import lombok.Getter;

import java.util.List;

@Getter
public class UserExcludedLinkListResponse {

    private List<UserExcludedLinkTagListDetailResponse> userLinkList;

    public UserExcludedLinkListResponse(List<UserExcludedLinkTagListDetailResponse> userLinkList) {
        this.userLinkList = userLinkList;
    }

}