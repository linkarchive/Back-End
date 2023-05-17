package project.linkarchive.backend.link.response.UserLinkList;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class LinkResponse {

    private Long linkId;
    private String url;
    private String title;
    private String description;
    private String thumbnail;
    private Long bookMarkCount;

    @QueryProjection
    public LinkResponse(Long linkId, String url, String title, String description, String thumbnail, Long bookMarkCount) {
        this.linkId = linkId;
        this.url = url;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.bookMarkCount = bookMarkCount;

    }

}
