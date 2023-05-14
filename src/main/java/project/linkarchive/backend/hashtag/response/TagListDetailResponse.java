package project.linkarchive.backend.hashtag.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TagListDetailResponse {

    private String tagName;

    @Builder
    public TagListDetailResponse(String tagName) {
        this.tagName = tagName;
    }

    public static TagListDetailResponse of(String tagName) {
        return TagListDetailResponse.builder()
                .tagName(tagName)
                .build();
    }

}
