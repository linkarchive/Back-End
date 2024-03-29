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
    private int bookmarkCount;
    private Boolean isRead;
    private Boolean isMark;
    private List<TagResponse> tagList;
    private LocalDateTime linkCreatedTime;
    private LocalDateTime linkUpdatedTime;

    @Builder
    public UserArchiveResponse(Long userId, String nickname, String profileImage, Long linkId, String url, String title, String description, String thumbnail, int bookmarkCount, Boolean isRead, Boolean isMark, List<TagResponse> tagList, LocalDateTime linkCreatedTime, LocalDateTime linkUpdatedTime) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.linkId = linkId;
        this.url = url;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.bookmarkCount = bookmarkCount;
        this.isRead = isRead;
        this.isMark = isMark;
        this.tagList = tagList;
        this.linkCreatedTime = linkCreatedTime;
        this.linkUpdatedTime = linkUpdatedTime;
    }

    public static UserArchiveResponse create(ArchiveResponse response, String preSignedUrl, Boolean isRead, Boolean isMark, List<TagResponse> tagList) {
        return UserArchiveResponse.builder()
                .userId(response.getUserId())
                .nickname(response.getNickname())
                .profileImage(preSignedUrl)
                .linkId(response.getLinkId())
                .url(response.getUrl())
                .title(response.getTitle())
                .description(response.getDescription())
                .thumbnail(response.getThumbnail())
                .bookmarkCount(response.getBookMarkCount())
                .isRead(isRead)
                .isMark(isMark)
                .tagList(tagList)
                .linkCreatedTime(response.getLinkCreatedTime())
                .linkUpdatedTime(response.getLinkUpdatedTime())
                .build();
    }

}