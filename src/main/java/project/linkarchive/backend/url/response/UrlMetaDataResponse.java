package project.linkarchive.backend.url.response;

import lombok.Getter;

@Getter
public class UrlMetaDataResponse {

    private String title;
    private String description;
    private String thumbnail;

    public UrlMetaDataResponse(String title, String description, String thumbnail) {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
    }

}