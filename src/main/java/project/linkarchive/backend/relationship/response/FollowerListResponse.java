package project.linkarchive.backend.relationship.response;

import lombok.Getter;

import java.util.List;

@Getter
public class FollowerListResponse {
    private List<FollowerResponse> followerResponses;

    public FollowerListResponse(List<FollowerResponse> followerResponse) {
        this.followerResponses = followerResponse;
    }

}
