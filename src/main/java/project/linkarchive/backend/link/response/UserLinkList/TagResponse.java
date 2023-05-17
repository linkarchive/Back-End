package project.linkarchive.backend.link.response.UserLinkList;

import lombok.Builder;
import lombok.Getter;
import project.linkarchive.backend.link.domain.UrlHashTag;

@Getter
public class TagResponse {

    private String tagName;

    @Builder
    public TagResponse(String tagName) {
        this.tagName = tagName;
    }

    public static TagResponse build(UrlHashTag urlHashTag) {
        return TagResponse.builder()
                .tagName(urlHashTag.getHashTag().getTag())
                .build();
    }

}
