package project.linkarchive.backend.link.response.trash;

import lombok.Getter;

import java.util.List;

@Getter
public class UserTrashLinkListResponse {

    private List<TrashLinkListResponse> linkList;
    private Boolean hasNext;

    public UserTrashLinkListResponse(List<TrashLinkListResponse> trashLinkListResponseList, Boolean hasNext) {
        this.linkList = trashLinkListResponseList;
        this.hasNext = hasNext;
    }

}