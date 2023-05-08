package project.linkarchive.backend.url.response.linkList;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class UserExcludedLinkListDetailResponse {

    private Long userId;
    private String name;
    private String profileImage;
    private Long urlId;
    private String link;
    private String title;
    private String description;
    private String thumbnail;
    private Long bookMarkCount;

    @QueryProjection
    public UserExcludedLinkListDetailResponse(Long userId, String name, String profileImage, Long urlId, String link, String title, String description, String thumbnail, Long bookMarkCount) {
        this.userId = userId;
        this.name = name;
        this.profileImage = profileImage;
        this.urlId = urlId;
        this.link = link;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.bookMarkCount = bookMarkCount;
    }

}