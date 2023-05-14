package project.linkarchive.backend.hashtag.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TagListDetailResponse {

    private String tagName;

    @Builder
    @QueryProjection
    public TagListDetailResponse(String tagName) {
        this.tagName = tagName;
    }

    public static TagListDetailResponse of(String tagName) {
        return TagListDetailResponse.builder()
                .tagName(tagName)
                .build();
    }

}
