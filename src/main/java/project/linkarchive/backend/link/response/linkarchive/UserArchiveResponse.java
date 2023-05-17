package project.linkarchive.backend.link.response.linkarchive;

import lombok.Builder;
import lombok.Getter;
import project.linkarchive.backend.link.response.UserLinkList.TagResponse;

import java.util.List;

@Getter
public class UserArchiveResponse {

    private Long userId;
    private String name;
    private String profileImage;
    private Long urlId;
    private String link;
    private String title;
    private String description;
    private String thumbnail;
    private Long bookMarkCount;
    private List<TagResponse> tagList;

    @Builder
    public UserArchiveResponse(Long userId, String name, String profileImage, Long urlId, String link, String title, String description, String thumbnail, Long bookMarkCount, List<TagResponse> tagList) {
        this.userId = userId;
        this.name = name;
        this.profileImage = profileImage;
        this.urlId = urlId;
        this.link = link;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.bookMarkCount = bookMarkCount;
        this.tagList = tagList;
    }

    public static UserArchiveResponse build(ArchiveResponse response, List<TagResponse> tagList) {
        return UserArchiveResponse.builder()
                .userId(response.getUserId())
                .name(response.getName())
                .profileImage(response.getProfileImage())
                .urlId(response.getUrlId())
                .link(response.getLink())
                .title(response.getTitle())
                .description(response.getDescription())
                .thumbnail(response.getThumbnail())
                .bookMarkCount(response.getBookMarkCount())
                .tagList(tagList)
                .build();
    }

}