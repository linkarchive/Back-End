package project.linkarchive.backend.link.response.userLinkList;

import lombok.Getter;
import project.linkarchive.backend.hashtag.response.TagListDetailResponse;

import java.util.List;

@Getter
public class DUserLinkListResponse {

    private List<TagListDetailResponse> userTagList;
    private List<UserLinkTagListDetailResponse> userLinkList;

    public DUserLinkListResponse(List<TagListDetailResponse> userTagList, List<UserLinkTagListDetailResponse> userLinkList) {
        this.userTagList = userTagList;
        this.userLinkList = userLinkList;
    }

}s