package project.linkarchive.backend.link.response.linkarchive;

import lombok.Getter;

import java.util.List;

@Getter
public class UserLinkArchiveResponse {

    private List<UserArchiveResponse> linkArchive;
    private Boolean hasNext;

    public UserLinkArchiveResponse(List<UserArchiveResponse> linkArchive, Boolean hasNext) {
        this.linkArchive = linkArchive;
        this.hasNext = hasNext;
    }

}