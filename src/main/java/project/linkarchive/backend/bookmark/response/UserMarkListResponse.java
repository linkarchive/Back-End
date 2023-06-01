package project.linkarchive.backend.bookmark.response;

import lombok.Getter;

import java.util.List;

@Getter
public class UserMarkListResponse {

    private List<UserMarkResponse> markList;
    private Boolean hasNext;

    public UserMarkListResponse(List<UserMarkResponse> markList, Boolean hasNext) {
        this.markList = markList;
        this.hasNext = hasNext;
    }

}