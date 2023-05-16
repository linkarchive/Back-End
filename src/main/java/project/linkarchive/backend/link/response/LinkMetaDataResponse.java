package project.linkarchive.backend.link.response;

import lombok.Getter;

@Getter
public class LinkMetaDataResponse {

    private String title;
    private String description;
    private String thumbnail;

    public LinkMetaDataResponse(String title, String description, String thumbnail) {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
    }

}