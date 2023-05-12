package project.linkarchive.backend.bookmark.response;

import lombok.Getter;
import project.linkarchive.backend.hashtag.response.TagListDetailResponse;

import java.util.List;

@Getter
public class UserMarkedLinkTagListDetailResponse {

    private Long markId;
    private Long urlId;
    private String link;
    private String title;
    private String description;
    private String thumbnail;
    private Long markCount;
    private List<TagListDetailResponse> markLinkTagList;

    public UserMarkedLinkTagListDetailResponse(Long markId, Long urlId, String link, String title, String description, String thumbnail, Long markCount, List<TagListDetailResponse> markLinkTagList) {
        this.markId = markId;
        this.urlId = urlId;
        this.link = link;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.markCount = markCount;
        this.markLinkTagList = markLinkTagList;
    }
    
}