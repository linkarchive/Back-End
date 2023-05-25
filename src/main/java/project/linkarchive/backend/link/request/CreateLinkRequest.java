package project.linkarchive.backend.link.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateLinkRequest {

    private String url;
    private String title;
    private String description;
    private String thumbnail;
    private List<String> tags = new ArrayList<>();

    public CreateLinkRequest(String url, String title, String description, String thumbnail, List<String> tags) {
        this.url = url;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.tags = tags;
    }

}