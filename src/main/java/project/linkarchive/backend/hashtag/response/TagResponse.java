package project.linkarchive.backend.hashtag.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import project.linkarchive.backend.link.domain.UrlHashTag;

@Getter
public class TagResponse {

    private String tagName;

    @Builder
    @QueryProjection
    public TagResponse(String tagName) {
        this.tagName = tagName;
    }

    public static TagResponse build(UrlHashTag urlHashTag) {
        return TagResponse.builder()
                .tagName(urlHashTag.getHashTag().getTag())
                .build();
    }

}
