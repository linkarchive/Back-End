package project.linkarchive.backend.url.response.otherUserLinkList;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import project.linkarchive.backend.user.domain.UserHashTag;

@Getter
public class OtherUserHashtagListResponse {

    private final String tag;

    @QueryProjection
    public OtherUserHashtagListResponse(UserHashTag userHashTag){
        this.tag = userHashTag.getHashTag().getTag();
    }
}
