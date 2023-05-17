package project.linkarchive.backend.link.request;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateLinkRequest {

    private String url;
    private String title;
    private String description;
    private String thumbnail;
    private List<String> tag;

    public CreateLinkRequest(String url, String title, String description, String thumbnail, List<String> tag) {
        this.url = url;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.tag = tag;
    }

}