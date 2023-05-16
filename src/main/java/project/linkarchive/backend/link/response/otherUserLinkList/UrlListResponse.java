package project.linkarchive.backend.link.response.otherUserLinkList;

import lombok.Getter;

import java.util.List;
@Getter

public class UrlListResponse {
    private Long urlId;
    private String link;
    private String title;
    private String description;
    private String thumbnail;
    private Long bookMarkCount;
    private List<UrlHashTagResponse> urlTagList;

    public UrlListResponse(Long urlId, String link, String title, String description, String thumbnail, Long bookMarkCount, List<UrlHashTagResponse> linkTagList) {
        this.urlId = urlId;
        this.link = link;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.bookMarkCount = bookMarkCount;
        this.urlTagList = linkTagList;
    }
}
