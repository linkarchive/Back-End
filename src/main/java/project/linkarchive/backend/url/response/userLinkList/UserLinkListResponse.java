package project.linkarchive.backend.url.response.userLinkList;

import lombok.Getter;
import project.linkarchive.backend.hashtag.response.TagListDetailResponse;

import java.util.List;

@Getter
public class UserLinkListResponse {

    private List<TagListDetailResponse> userTagList;
    private List<UserLinkTagListDetailResponse> userLinkList;

    public UserLinkListResponse(List<TagListDetailResponse> userTagList, List<UserLinkTagListDetailResponse> userLinkList) {
        this.userTagList = userTagList;
        this.userLinkList = userLinkList;
    }

}