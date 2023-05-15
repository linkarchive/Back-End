package project.linkarchive.backend.hashtag.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import project.linkarchive.backend.url.domain.UrlHashTag;

@Getter
public class TagListDetailResponse {

    private String tagName;

    @Builder
    @QueryProjection
    public TagListDetailResponse(String tagName) {
        this.tagName = tagName;
    }

    public static TagListDetailResponse of(UrlHashTag urlHashTag) {
        return TagListDetailResponse.builder()
                .tagName(urlHashTag.getHashTag().getTag())
                .build();
    }

}
