package project.linkarchive.backend.hashtag.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TagCreateRequest {

    private String tag;

    public TagCreateRequest(String tag) {
        this.tag = tag;
    }

}