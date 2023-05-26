package project.linkarchive.backend.link.response.linkList;

import lombok.Builder;
import lombok.Getter;
import project.linkarchive.backend.hashtag.response.TagResponse;

import java.util.List;

@Getter
public class UserLinkResponse {

    private Long urlId;
    private String link;
    private String title;
    private String description;
    private String thumbnail;
    private Long bookMarkCount;
    private Boolean isRead;
    private List<String> tagList;

    @Builder
    public UserLinkResponse(Long urlId, String link, String title, String description, String thumbnail, Long bookMarkCount, Boolean isRead, List<String> tagList) {
        this.urlId = urlId;
        this.link = link;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.bookMarkCount = bookMarkCount;
        this.isRead = isRead;
        this.tagList = tagList;
    }

    public static UserLinkResponse build(LinkResponse response, Boolean isRead, List<String> tagList) {
        return UserLinkResponse.builder()
                .urlId(response.getLinkId())
                .link(response.getUrl())
                .title(response.getTitle())
                .description(response.getDescription())
                .thumbnail(response.getThumbnail())
                .bookMarkCount(response.getBookMarkCount())
                .isRead(isRead)
                .tagList(tagList)
                .build();
    }

}
