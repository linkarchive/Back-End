package project.linkarchive.backend.url.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UrlCreateRequest {

    private String thumbnail;
    private String title;
    private String url;
    private List<String> tag;

    public UrlCreateRequest(String thumbnail, String title, String url, List<String> tag) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.url = url;
        this.tag = tag;
    }

}