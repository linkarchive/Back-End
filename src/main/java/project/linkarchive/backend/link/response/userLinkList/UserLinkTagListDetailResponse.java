package project.linkarchive.backend.link.response.userLinkList;

import lombok.Getter;

import java.util.List;

@Getter
public class UserLinkTagListDetailResponse {

    private Long urlId;
    private String link;
    private String title;
    private String description;
    private String thumbnail;
    private Long bookMarkCount;
    private List<UserTagListDetailResponse> linkTagList;

    public UserLinkTagListDetailResponse(Long urlId, String link, String title, String description, String thumbnail, Long bookMarkCount, List<UserTagListDetailResponse> linkTagList) {
        this.urlId = urlId;
        this.link = link;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.bookMarkCount = bookMarkCount;
        this.linkTagList = linkTagList;
    }

}