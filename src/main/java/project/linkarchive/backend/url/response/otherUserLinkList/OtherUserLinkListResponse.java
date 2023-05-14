package project.linkarchive.backend.url.response.otherUserLinkList;

import lombok.Getter;

import java.util.List;

@Getter
public class OtherUserLinkListResponse {
    private final List<OtherUserHashtagListResponse> userTagList;
    private final List<OtherUserUrlListResponse> urlList;

    public OtherUserLinkListResponse(List<OtherUserUrlListResponse> urlList, List<OtherUserHashtagListResponse> otherUserHashtagListResponse) {
        this.urlList = urlList;
        this.userTagList = otherUserHashtagListResponse;
    }
}

