package project.linkarchive.backend.url.response.linkList;

import lombok.Getter;

import java.util.List;

@Getter
public class LinkListResponse {

    private List<LinkTagListDetailResponse> urlLinkListDetailResponseList;

    public LinkListResponse(List<LinkTagListDetailResponse> urlLinkListDetailResponseList) {
        this.urlLinkListDetailResponseList = urlLinkListDetailResponseList;
    }


}