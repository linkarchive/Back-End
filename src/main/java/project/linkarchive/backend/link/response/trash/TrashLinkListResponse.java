package project.linkarchive.backend.link.response.trash;

import lombok.Builder;
import lombok.Getter;
import project.linkarchive.backend.hashtag.response.TagResponse;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class TrashLinkListResponse {

    private Long linkId;
    private String url;
    private String title;
    private String description;
    private String thumbnail;
    private int bookmarkCount;
    private List<TagResponse> tagList;
    private LocalDateTime linkCreatedTime;

    @Builder
    public TrashLinkListResponse(Long linkId, String url, String title, String description, String thumbnail, int bookmarkCount, List<TagResponse> tagList, LocalDateTime linkCreatedTime) {
        this.linkId = linkId;
        this.url = url;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.bookmarkCount = bookmarkCount;
        this.tagList = tagList;
        this.linkCreatedTime = linkCreatedTime;
    }

    public static TrashLinkListResponse create(TrashLinkResponse trashLinkResponse, List<TagResponse> tagList) {
        return TrashLinkListResponse.builder()
                .linkId(trashLinkResponse.getLinkId())
                .url(trashLinkResponse.getUrl())
                .title(trashLinkResponse.getTitle())
                .description(trashLinkResponse.getDescription())
                .thumbnail(trashLinkResponse.getThumbnail())
                .bookmarkCount(trashLinkResponse.getBookMarkCount())
                .tagList(tagList)
                .linkCreatedTime(trashLinkResponse.getLinkCreatedTime())
                .build();
    }

}