package project.linkarchive.backend.link.response.linkList;

import lombok.Builder;
import lombok.Getter;
import project.linkarchive.backend.hashtag.response.TagResponse;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class UserLinkResponse {

    private Long linkId;
    private String url;
    private String title;
    private String description;
    private String thumbnail;
    private Long bookMarkCount;
    private Boolean isRead;
    private Boolean isMark;
    private List<TagResponse> tagList;
    private LocalDateTime linkCreatedTime;
    private LocalDateTime linkUpdatedTime;

    @Builder
    public UserLinkResponse(Long linkId, String url, String title, String description, String thumbnail, Long bookMarkCount, Boolean isRead, Boolean isMark, List<TagResponse> tagList, LocalDateTime linkCreatedTime, LocalDateTime linkUpdatedTime) {
        this.linkId = linkId;
        this.url = url;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.bookMarkCount = bookMarkCount;
        this.isRead = isRead;
        this.isMark = isMark;
        this.tagList = tagList;
        this.linkCreatedTime = linkCreatedTime;
        this.linkUpdatedTime = linkUpdatedTime;
    }

    public static UserLinkResponse create(LinkResponse response, Boolean isRead, Boolean isMark, List<TagResponse> tagList) {
        return UserLinkResponse.builder()
                .linkId(response.getLinkId())
                .url(response.getUrl())
                .title(response.getTitle())
                .description(response.getDescription())
                .thumbnail(response.getThumbnail())
                .bookMarkCount(response.getBookMarkCount())
                .isRead(isRead)
                .isMark(isMark)
                .tagList(tagList)
                .linkCreatedTime(response.getLinkCreatedTime())
                .linkUpdatedTime(response.getLinkUpdatedTime())
                .build();
    }

}
