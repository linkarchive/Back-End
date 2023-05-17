package project.linkarchive.backend.link.response.linkarchive;

import lombok.Getter;

import java.util.List;

@Getter
public class UserLinkArchiveResponse {

    private List<UserArchiveResponse> linkArchive;

    public UserLinkArchiveResponse(List<UserArchiveResponse> linkArchive) {
        this.linkArchive = linkArchive;
    }

}