package project.linkarchive.backend.bookmark.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MarkResponse {

    private Long markId;
    private Long linkId;
    private String url;
    private String title;
    private String description;
    private String thumbnail;
    private Long bookMarkCount;
    private LocalDateTime bookMarkedTime;

    @QueryProjection
    public MarkResponse(Long markId, Long linkId, String url, String title, String description, String thumbnail, Long bookMarkCount, LocalDateTime bookMarkedTime) {
        this.markId = markId;
        this.linkId = linkId;
        this.url = url;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.bookMarkCount = bookMarkCount;
        this.bookMarkedTime = bookMarkedTime;
    }

}