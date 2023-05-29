package project.linkarchive.backend.link.response;

import lombok.Getter;

@Getter
public class LinkMetaDataResponse {

    private String titleText;
    private String metaTitle;
    private String metaDescription;
    private String metaThumbnail;

    public LinkMetaDataResponse(String titleText, String metaTitle, String metaDescription, String metaThumbnail) {
        this.titleText = titleText;
        this.metaTitle = metaTitle;
        this.metaDescription = metaDescription;
        this.metaThumbnail = metaThumbnail;
    }

}