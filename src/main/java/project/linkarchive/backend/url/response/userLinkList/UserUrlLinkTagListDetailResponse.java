package project.linkarchive.backend.url.response.userLinkList;

import lombok.Getter;

import java.util.List;

@Getter
public class UserUrlLinkTagListDetailResponse {

    private Long urlId;
    private String link;
    private String title;
    private String description;
    private String thumbnail;
    private Long bookMarkCount;
    private List<UserUrlTagListDetailResponse> userUrlTagListDetailResponseList;

    public UserUrlLinkTagListDetailResponse(Long urlId, String link, String title, String description, String thumbnail, Long bookMarkCount, List<UserUrlTagListDetailResponse> userUrlTagListDetailResponseList) {
        this.urlId = urlId;
        this.link = link;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.bookMarkCount = bookMarkCount;
        this.userUrlTagListDetailResponseList = userUrlTagListDetailResponseList;
    }

}