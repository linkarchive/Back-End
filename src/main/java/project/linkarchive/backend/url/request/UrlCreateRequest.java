package project.linkarchive.backend.url.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UrlCreateRequest {

    private String link;
    private String title;
    private String description;
    private String thumbnail;
    private List<String> tag;

    public UrlCreateRequest(String link, String title, String description, String thumbnail, List<String> tag) {
        this.link = link;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.tag = tag;
    }

}