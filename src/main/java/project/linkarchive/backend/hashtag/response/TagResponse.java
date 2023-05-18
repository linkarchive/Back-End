package project.linkarchive.backend.hashtag.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import project.linkarchive.backend.link.domain.UrlHashTag;

import java.util.Objects;

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