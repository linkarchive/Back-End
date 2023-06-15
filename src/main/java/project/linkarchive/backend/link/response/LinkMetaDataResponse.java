package project.linkarchive.backend.link.response;

import lombok.Getter;

@Getter
public class LinkMetaDataResponse {

    private String metaTitle;
    private String metaDescription;
    private String metaThumbnail;

    public LinkMetaDataResponse(String metaTitle, String metaDescription, String metaThumbnail) {
        this.metaTitle = metaTitle;
        this.metaDescription = metaDescription;
        this.metaThumbnail = metaThumbnail;
    }

}