package project.linkarchive.backend.url.response.otherUserLinkList;

import lombok.Getter;

import java.util.List;

@Getter
public class LinkListResponse {
    private List<UrlListResponse> userLinkList;

    public LinkListResponse(List<UrlListResponse> userLinkList) {
        this.userLinkList = userLinkList;
    }
}
