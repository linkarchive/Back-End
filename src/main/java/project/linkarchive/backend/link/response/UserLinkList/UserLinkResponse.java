package project.linkarchive.backend.link.response.UserLinkList;

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
    private List<TagResponse> tagList;

    @Builder
    public UserLinkResponse(Long urlId, String link, String title, String description, String thumbnail, Long bookMarkCount, List<TagResponse> tagList) {
        this.urlId = urlId;
        this.link = link;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.bookMarkCount = bookMarkCount;
        this.tagList = tagList;
    }

    public static UserLinkResponse build(LinkResponse response, List<TagResponse> tagList) {
        return UserLinkResponse.builder()
                .urlId(response.getLinkId())
                .link(response.getUrl())
                .title(response.getTitle())
                .description(response.getDescription())
                .thumbnail(response.getThumbnail())
                .bookMarkCount(response.getBookMarkCount())
                .tagList(tagList)
                .build();
    }

}
