package project.linkarchive.backend.relationship.response;

import lombok.Getter;

import java.util.List;

@Getter
public class FollowListResponse {

    private List<FollowResponse> followResponseList;

    public FollowListResponse(List<FollowResponse> followResponseList){
        this.followResponseList = followResponseList;
    }

}