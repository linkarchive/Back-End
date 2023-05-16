package project.linkarchive.backend.url.response.RefactorUserLinkList;

import lombok.Getter;

import java.util.List;
@Getter

public class UserLinkResponse {
    private Long urlId;
    private String link;
    private String title;
    private String description;
    private String thumbnail;
    private Long bookMarkCount;
    private List<TagResponse> tagList;

    public UserLinkResponse(Long urlId, String link, String title, String description, String thumbnail, Long bookMarkCount, List<TagResponse> linkTagList) {
        this.urlId = urlId;
        this.link = link;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.bookMarkCount = bookMarkCount;
        this.tagList = linkTagList;
    }
}
