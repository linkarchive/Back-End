package project.linkarchive.backend.link.response.trash;

import lombok.Getter;

import java.util.List;

@Getter
public class UserTrashLinkListResponse {

    private List<TrashLinkListResponse> trashLinkList;
    private Boolean hasNext;

    public UserTrashLinkListResponse(List<TrashLinkListResponse> trashLinkListResponseList, Boolean hasNext) {
        this.trashLinkList = trashLinkListResponseList;
        this.hasNext = hasNext;
    }

}