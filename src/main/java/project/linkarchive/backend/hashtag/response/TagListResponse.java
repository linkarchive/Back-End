package project.linkarchive.backend.hashtag.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TagListResponse {

    private String tagName;

    @Builder
    public TagListResponse(String tagName) {
        this.tagName = tagName;
    }

    public static TagListResponse of(String tagName) {
        return TagListResponse.builder()
                .tagName(tagName)
                .build();
    }

}