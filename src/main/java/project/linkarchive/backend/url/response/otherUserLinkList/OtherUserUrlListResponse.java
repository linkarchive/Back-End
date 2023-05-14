package project.linkarchive.backend.url.response.otherUserLinkList;

import lombok.Getter;
import project.linkarchive.backend.url.domain.Url;

import java.util.List;

@Getter
public class OtherUserUrlListResponse {
    private final Long urlId;
    private final String link;
    private final String title;
    private final String description;
    private final String thumbnail;
    private final Long bookmarkCount;

    private final List<String> urlHashtags;

    public OtherUserUrlListResponse(Url url){
        this.urlId = url.getId();
        this.link = url.getLink();
        this.title = url.getTitle();
        this.description = url.getDescription();
        this.thumbnail = url.getThumbnail();
        this.bookmarkCount = url.getBookMarkCount();
        this.urlHashtags = url.getUrlHashTagList();
    }

}
