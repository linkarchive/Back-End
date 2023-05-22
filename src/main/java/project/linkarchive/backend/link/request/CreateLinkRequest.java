package project.linkarchive.backend.link.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateLinkRequest {

    private String url;
    private String title;
    private String description;
    private String thumbnail;
    private ArrayList<String> tag;

    public CreateLinkRequest(String url, String title, String description, String thumbnail, ArrayList<String> tag) {
        this.url = url;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.tag = tag;
    }

}