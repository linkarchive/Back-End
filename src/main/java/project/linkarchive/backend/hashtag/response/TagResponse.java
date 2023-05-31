package project.linkarchive.backend.hashtag.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import project.linkarchive.backend.link.domain.LinkHashTag;

import java.util.Objects;

@Getter
public class TagResponse {

    private Long tagId;
    private String tagName;

    @Builder
    @QueryProjection
    public TagResponse(Long tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }

    public TagResponse(String tagName) {
        this.tagName = tagName;
    }

    public static TagResponse build(LinkHashTag linkHashTag) {
        return TagResponse.builder()
                .tagId(linkHashTag.getHashTag().getId())
                .tagName(linkHashTag.getHashTag().getTag())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagResponse that = (TagResponse) o;
        return Objects.equals(tagName, that.tagName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagName);
    }

}