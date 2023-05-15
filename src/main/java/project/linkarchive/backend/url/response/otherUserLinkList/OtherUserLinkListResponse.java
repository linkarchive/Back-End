package project.linkarchive.backend.url.response.otherUserLinkList;

import lombok.Getter;
import project.linkarchive.backend.hashtag.response.TagListDetailResponse;

import java.util.List;

@Getter
public class OtherUserLinkListResponse {
    private List<TagListDetailResponse> userTagList;
    private List<UrlListResponse> userLinkList;

    public OtherUserLinkListResponse(List<TagListDetailResponse> userTagList, List<UrlListResponse> userLinkList) {
        this.userTagList = userTagList;
        this.userLinkList = userLinkList;
    }
}
