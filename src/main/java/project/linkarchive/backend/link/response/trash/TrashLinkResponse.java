package project.linkarchive.backend.link.response.trash;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TrashLinkResponse {

    private Long linkId;
    private String url;
    private String title;
    private String description;
    private String thumbnail;
    private int bookMarkCount;
    private LocalDateTime linkCreatedTime;

    @QueryProjection
    public TrashLinkResponse(Long linkId, String url, String title, String description, String thumbnail, int bookMarkCount, LocalDateTime linkCreatedTime) {
        this.linkId = linkId;
        this.url = url;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.bookMarkCount = bookMarkCount;
        this.linkCreatedTime = linkCreatedTime;
    }

}