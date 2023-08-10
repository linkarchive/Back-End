package project.linkarchive.backend.relationship.response;

import lombok.Getter;

import java.util.List;

@Getter
public class FollowListResponse {

    private List<FollowResponse> followList;

    public FollowListResponse(List<FollowResponse> followList){
        this.followList = followList;
    }

}