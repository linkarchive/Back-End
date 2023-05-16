package project.linkarchive.backend.link.response.linkList;

import lombok.Builder;
import lombok.Getter;
import project.linkarchive.backend.hashtag.response.TagListDetailResponse;

import java.util.List;

@Getter
public class UserExcludedLinkTagListDetailResponse {

    private final Long userId;
    private final String name;
    private final String profileImage;
    private final Long urlId;
    private final String link;
    private final String title;
    private final String description;
    private final String thumbnail;
    private final Long bookMarkCount;
    private final List<TagListDetailResponse> linkTagList;

    @Builder
    public UserExcludedLinkTagListDetailResponse(Long userId, String name, String profileImage, Long urlId, String link, String title, String description, String thumbnail, Long bookMarkCount, List<TagListDetailResponse> linkTagList) {
        this.userId = userId;
        this.name = name;
        this.profileImage = profileImage;
        this.urlId = urlId;
        this.link = link;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.bookMarkCount = bookMarkCount;
        this.linkTagList = linkTagList;
    }

    public static UserExcludedLinkTagListDetailResponse of(UserExcludedLinkListDetailResponse response, List<TagListDetailResponse> linkTagList) {
        return UserExcludedLinkTagListDetailResponse.builder()
                .userId(response.getUserId())
                .name(response.getName())
                .profileImage(response.getProfileImage())
                .urlId(response.getUrlId())
                .link(response.getLink())
                .title(response.getTitle())
                .description(response.getDescription())
                .thumbnail(response.getThumbnail())
                .bookMarkCount(response.getBookMarkCount())
                .linkTagList(linkTagList)
                .build();
    }
}