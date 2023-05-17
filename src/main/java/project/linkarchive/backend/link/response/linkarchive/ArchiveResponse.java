package project.linkarchive.backend.link.response.linkarchive;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ArchiveResponse {

    private Long userId;
    private String name;
    private String profileImage;
    private Long linkId;
    private String link;
    private String title;
    private String description;
    private String thumbnail;
    private Long bookMarkCount;

    @QueryProjection
    public ArchiveResponse(Long userId, String name, String profileImage, Long linkId, String link, String title, String description, String thumbnail, Long bookMarkCount) {
        this.userId = userId;
        this.name = name;
        this.profileImage = profileImage;
        this.linkId = linkId;
        this.link = link;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.bookMarkCount = bookMarkCount;
    }

}