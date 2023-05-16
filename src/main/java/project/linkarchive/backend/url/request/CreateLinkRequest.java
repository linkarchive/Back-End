package project.linkarchive.backend.url.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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