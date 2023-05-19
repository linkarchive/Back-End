package project.linkarchive.backend.hashtag.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateTagRequest {

    private String tag;

    public CreateTagRequest(String tag) {
        this.tag = tag;
    }

}