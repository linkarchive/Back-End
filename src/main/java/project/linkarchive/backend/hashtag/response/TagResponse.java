package project.linkarchive.backend.hashtag.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class TagResponse {

    private String tagName;

    @QueryProjection
    public TagResponse(String tagName) {
        this.tagName = tagName;
    }

}
