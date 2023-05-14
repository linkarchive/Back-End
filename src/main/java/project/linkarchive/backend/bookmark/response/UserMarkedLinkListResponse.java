package project.linkarchive.backend.bookmark.response;

import lombok.Getter;
import project.linkarchive.backend.user.response.UserTagList30Response;

import java.util.List;

@Getter
public class UserMarkedLinkListResponse {

    private List<UserTagList30Response> userTagList;
    List<UserMarkedLinkTagListDetailResponse> markLinkList;

    public UserMarkedLinkListResponse(List<UserTagList30Response> userTagList, List<UserMarkedLinkTagListDetailResponse> markLinkList) {
        this.userTagList = userTagList;
        this.markLinkList = markLinkList;
    }

}