package project.linkarchive.backend.link.response.userLinkList;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class UserLinkListDetailResponse {

    private Long urlId;
    private String link;
    private String title;
    private String description;
    private String thumbnail;
    private Long bookMarkCount;

    @QueryProjection
    public UserLinkListDetailResponse(Long urlId, String link, String title, String description, String thumbnail, Long bookMarkCount) {
        this.urlId = urlId;
        this.link = link;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.bookMarkCount = bookMarkCount;
    }

}