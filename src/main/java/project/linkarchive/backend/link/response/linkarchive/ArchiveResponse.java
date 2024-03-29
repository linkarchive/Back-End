package project.linkarchive.backend.link.response.linkarchive;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArchiveResponse {

    private Long userId;
    private String nickname;
    private String profileImage;
    private Long linkId;
    private String url;
    private String title;
    private String description;
    private String thumbnail;
    private int bookMarkCount;
    private LocalDateTime linkCreatedTime;
    private LocalDateTime linkUpdatedTime;

    @QueryProjection
    public ArchiveResponse(Long userId, String nickname, String profileImage, Long linkId, String url, String title, String description, String thumbnail, int bookMarkCount, LocalDateTime linkCreatedTime, LocalDateTime linkUpdatedTime) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.linkId = linkId;
        this.url = url;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.bookMarkCount = bookMarkCount;
        this.linkCreatedTime = linkCreatedTime;
        this.linkUpdatedTime = linkUpdatedTime;
    }

}