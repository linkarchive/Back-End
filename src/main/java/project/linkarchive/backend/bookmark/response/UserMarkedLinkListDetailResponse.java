package project.linkarchive.backend.bookmark.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class UserMarkedLinkListDetailResponse {

    private Long markId;
    private Long urlId;
    private String link;
    private String title;
    private String description;
    private String thumbnail;
    private Long markCount;

    @QueryProjection
    public UserMarkedLinkListDetailResponse(Long markId, Long urlId, String link, String title, String description, String thumbnail, Long markCount) {
        this.markId = markId;
        this.urlId = urlId;
        this.link = link;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.markCount = markCount;
    }

}