package project.linkarchive.backend.url.response.linkList;

import lombok.Getter;

import java.util.List;

@Getter
public class UserExcludedLinkTagListDetailResponse {

    private Long userId;
    private String name;
    private String profileImage;
    private Long urlId;
    private String link;
    private String title;
    private String description;
    private String thumbnail;
    private Long bookMarkCount;
    private List<UserExcludedTagListDetailResponse> linkTagList;

    public UserExcludedLinkTagListDetailResponse(Long userId, String name, String profileImage, Long urlId, String link, String title, String description, String thumbnail, Long bookMarkCount, List<UserExcludedTagListDetailResponse> linkTagList) {
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

}