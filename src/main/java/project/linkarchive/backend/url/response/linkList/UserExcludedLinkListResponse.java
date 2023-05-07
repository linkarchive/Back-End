package project.linkarchive.backend.url.response.linkList;

import lombok.Getter;

import java.util.List;

@Getter
public class UserExcludedLinkListResponse {

    private List<UserExcludedLinkTagListDetailResponse> userExcludedLinkTagListDetailResponseList;

    public UserExcludedLinkListResponse(List<UserExcludedLinkTagListDetailResponse> userExcludedLinkTagListDetailResponseList) {
        this.userExcludedLinkTagListDetailResponseList = userExcludedLinkTagListDetailResponseList;
    }

}