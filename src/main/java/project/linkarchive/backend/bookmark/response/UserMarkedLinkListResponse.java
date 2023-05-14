package project.linkarchive.backend.bookmark.response;

import lombok.Getter;
import project.linkarchive.backend.hashtag.response.TagListResponse;

import java.util.List;

@Getter
public class UserMarkedLinkListResponse {

    private List<TagListResponse> userTagList;
    List<UserMarkedLinkTagListDetailResponse> markLinkList;

    public UserMarkedLinkListResponse(List<TagListResponse> userTagList, List<UserMarkedLinkTagListDetailResponse> markLinkList) {
        this.userTagList = userTagList;
        this.markLinkList = markLinkList;
    }

}