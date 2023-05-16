package project.linkarchive.backend.url.response.otherUserLinkList;

import lombok.Getter;
import project.linkarchive.backend.hashtag.response.TagListDetailResponse;

import java.util.List;

@Getter
public class DOtherUserLinkListResponse {
    private List<TagListDetailResponse> userTagList;
    private List<UserLinkResponse> userLinkList;

    public DOtherUserLinkListResponse(List<TagListDetailResponse> userTagList, List<UserLinkResponse> userLinkList) {
        this.userTagList = userTagList;
        this.userLinkList = userLinkList;
    }
}
