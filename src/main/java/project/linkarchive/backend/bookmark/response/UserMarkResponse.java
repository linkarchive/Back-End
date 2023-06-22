package project.linkarchive.backend.bookmark.response;

import lombok.Builder;
import lombok.Getter;
import project.linkarchive.backend.hashtag.response.TagResponse;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class UserMarkResponse {

    private Long markId;
    private Long linkId;
    private String url;
    private String title;
    private String description;
    private String thumbnail;
    private Long bookMarkCount;
    private LocalDateTime bookMarkedTime;
    private Boolean isRead;
    private Boolean isMark;
    private List<TagResponse> tagList;

    @Builder
    public UserMarkResponse(Long markId, Long linkId, String url, String title, String description, String thumbnail, Long bookMarkCount, LocalDateTime bookMarkedTime, Boolean isRead, Boolean isMark, List<TagResponse> tagList) {
        this.markId = markId;
        this.linkId = linkId;
        this.url = url;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.bookMarkCount = bookMarkCount;
        this.bookMarkedTime = bookMarkedTime;
        this.isRead = isRead;
        this.isMark = isMark;
        this.tagList = tagList;
    }

    public static UserMarkResponse build(MarkResponse response, Boolean isRead, Boolean isMark, List<TagResponse> tagList) {
        return UserMarkResponse.builder()
                .markId(response.getMarkId())
                .linkId(response.getLinkId())
                .url(response.getUrl())
                .title(response.getTitle())
                .description(response.getDescription())
                .thumbnail(response.getThumbnail())
                .bookMarkCount(response.getBookMarkCount())
                .bookMarkedTime(response.getBookMarkedTime())
                .isRead(isRead)
                .isMark(isMark)
                .tagList(tagList)
                .build();
    }

}