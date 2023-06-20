package project.linkarchive.backend.link.response.linkarchive;

import lombok.Builder;
import lombok.Getter;
import project.linkarchive.backend.hashtag.response.TagResponse;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class UserArchiveResponse {

    private Long userId;
    private String nickname;
    private String profileImage;
    private Long linkId;
    private String url;
    private String title;
    private String description;
    private String thumbnail;
    private LocalDateTime linkCreatedTime;
    private Long bookMarkCount;
    private Boolean isRead;
    private Boolean isMark;
    private List<TagResponse> tagList;

    @Builder
    public UserArchiveResponse(Long userId, String nickname, String profileImage, Long linkId, String url, String title, String description, String thumbnail, LocalDateTime linkCreatedTime, Long bookMarkCount, Boolean isRead, Boolean isMark, List<TagResponse> tagList) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.linkId = linkId;
        this.url = url;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.linkCreatedTime = linkCreatedTime;
        this.bookMarkCount = bookMarkCount;
        this.isRead = isRead;
        this.isMark = isMark;
        this.tagList = tagList;
    }

    public static UserArchiveResponse build(ArchiveResponse response, String preSignedUrl, Boolean isRead, Boolean isMark, List<TagResponse> tagList) {
        return UserArchiveResponse.builder()
                .userId(response.getUserId())
                .nickname(response.getNickname())
                .profileImage(preSignedUrl)
                .linkId(response.getLinkId())
                .url(response.getUrl())
                .title(response.getTitle())
                .description(response.getDescription())
                .thumbnail(response.getThumbnail())
                .linkCreatedTime(response.getLinkCreatedTime())
                .bookMarkCount(response.getBookMarkCount())
                .isRead(isRead)
                .isMark(isMark)
                .tagList(tagList)
                .build();
    }

}