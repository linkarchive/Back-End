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
    private LocalDateTime createdAt;
    private Boolean isRead;
    private Boolean isMark;
    private List<TagResponse> tagList;

    @Builder
    public UserLinkResponse(Long linkId, String url, String title, String description, String thumbnail, Long bookMarkCount, Boolean isRead, Boolean isMark, LocalDateTime createdAt, List<TagResponse> tagList) {
        this.linkId = linkId;
        this.url = url;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.bookMarkCount = bookMarkCount;
        this.createdAt = createdAt;
        this.isRead = isRead;
        this.isMark = isMark;
        this.tagList = tagList;
    }

    public static UserLinkResponse build(LinkResponse response, Boolean isRead, Boolean isMark, List<TagResponse> tagList) {
        return UserLinkResponse.builder()
                .linkId(response.getLinkId())
                .url(response.getUrl())
                .title(response.getTitle())
                .description(response.getDescription())
                .thumbnail(response.getThumbnail())
                .bookMarkCount(response.getBookMarkCount())
                .createdAt(response.getCreatedAt())
                .isRead(isRead)
                .isMark(isMark)
                .tagList(tagList)
                .build();
    }

}
