package project.linkarchive.backend.url.response.linkList;

import lombok.Getter;

import java.util.List;

@Getter
public class LinkTagListDetailResponse {

    private Long urlId;
    private String link;
    private String title;
    private String description;
    private String thumbnail;
    private Long bookMarkCount;
    private List<TagListDetailResponse> tagListDetailResponseList;

    public LinkTagListDetailResponse(Long urlId, String link, String title, String description, String thumbnail, Long bookMarkCount, List<TagListDetailResponse> tagListDetailResponseList) {
        this.urlId = urlId;
        this.link = link;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.bookMarkCount = bookMarkCount;
        this.tagListDetailResponseList = tagListDetailResponseList;
    }

}