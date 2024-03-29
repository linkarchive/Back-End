package project.linkarchive.backend.link.response.linkList;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LinkResponse {

    private Long linkId;
    private String url;
    private String title;
    private String description;
    private String thumbnail;
    private int bookmarkCount;
    private LocalDateTime linkCreatedTime;
    private LocalDateTime linkUpdatedTime;

    @QueryProjection
    public LinkResponse(Long linkId, String url, String title, String description, String thumbnail, int bookmarkCount, LocalDateTime linkCreatedTime, LocalDateTime linkUpdatedTime) {
        this.linkId = linkId;
        this.url = url;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.bookmarkCount = bookmarkCount;
        this.linkCreatedTime = linkCreatedTime;
        this.linkUpdatedTime = linkUpdatedTime;
    }

}
