package project.linkarchive.backend.link.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.link.response.LinkMetaDataResponse;
import project.linkarchive.backend.link.response.linkarchive.UserLinkArchiveResponse;
import project.linkarchive.backend.link.response.linkList.UserLinkListResponse;
import project.linkarchive.backend.link.service.LinkQueryService;
import project.linkarchive.backend.security.AuthInfo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
@PreAuthorize("isAuthenticated()")
public class LinkQueryController {

    private final LinkQueryService linkQueryService;

    public LinkQueryController(LinkQueryService linkQueryService) {
        this.linkQueryService = linkQueryService;
    }

    @GetMapping("/link/metadata")
    public ResponseEntity<LinkMetaDataResponse> getUrlMetaData(@RequestParam(value = "link") String link) {
        URL urlObject;
        try {
            urlObject = new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) urlObject.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        Document document;
        try {
            document = Jsoup.connect(link).get();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        String title = document.select("meta[property=og:title]").attr("content");
        String description = document.select("meta[property=og:description]").attr("content");
        String thumbnail = document.select("meta[property=og:image]").attr("content");

        LinkMetaDataResponse linkMetaDataResponse = new LinkMetaDataResponse(title, description, thumbnail);
        return ResponseEntity.ok(linkMetaDataResponse);
    }

    // 사용자별 링크 리스트 조회 003
    @GetMapping("/links/user/{userId}")
    public ResponseEntity<UserLinkListResponse> getUserLinkList(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "linkId", required = false) Long lastLinkId,
            @PageableDefault Pageable pageable
    ) {
        UserLinkListResponse userLinkListResponse = linkQueryService.getUserLinkList(userId, pageable, lastLinkId);
        return ResponseEntity.ok(userLinkListResponse);
    }

    // 사용자별 북마크 리스트 조회 010
    @GetMapping("/mark/links/user/{userId}")
    public ResponseEntity<UserLinkListResponse> getMarkedLinkList(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "linkId", required = false) Long lastLinkId,
            @PageableDefault Pageable pageable
    ) {
        UserLinkListResponse userLinkListResponse = linkQueryService.getMarkedLinkList(userId, lastLinkId, pageable);
        return ResponseEntity.ok(userLinkListResponse);
    }

    // 로그인한 유저를 제외한 사용자들의 링크 리스트 조회 013
    @GetMapping("/links/archive")
    public ResponseEntity<UserLinkArchiveResponse> getLinkArchive(
            @PageableDefault Pageable pageable,
            @RequestParam(value = "linkId", required = false) Long lastLinkId,
            AuthInfo authInfo
    ) {
        UserLinkArchiveResponse userLinkArchiveResponse = linkQueryService.getLinkArchive(pageable, lastLinkId, authInfo.getId());
        return ResponseEntity.ok(userLinkArchiveResponse);
    }

}