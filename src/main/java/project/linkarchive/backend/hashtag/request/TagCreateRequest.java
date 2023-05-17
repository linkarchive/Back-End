package project.linkarchive.backend.hashtag.request;

import lombok.Getter;

@Getter
public class TagCreateRequest {

    private String tag;

    public TagCreateRequest(String tag) {
        this.tag = tag;
    }

}