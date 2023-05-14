package project.linkarchive.backend.bookmark.response;

import lombok.Getter;
import project.linkarchive.backend.hashtag.response.TagListDetailResponse;

import java.util.List;

@Getter
public class UserMarkedLinkListResponse {

    List<UserMarkedLinkTagListDetailResponse> markLinkList;
    private List<TagListDetailResponse> userTagList;

    public UserMarkedLinkListResponse(List<TagListDetailResponse> userTagList, List<UserMarkedLinkTagListDetailResponse> markLinkList) {
        this.userTagList = userTagList;
        this.markLinkList = markLinkList;
    }

}