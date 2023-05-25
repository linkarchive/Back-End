package project.linkarchive.backend.link.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.link.response.LinkMetaDataResponse;
import project.linkarchive.backend.link.response.linkList.UserLinkListResponse;
import project.linkarchive.backend.link.response.linkarchive.UserLinkArchiveResponse;
import project.linkarchive.backend.link.service.LinkQueryService;
import project.linkarchive.backend.security.AuthInfo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
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

    // 내 링크 리스트 조회 - 로그인 필요 O
    @GetMapping("/links/user")
    public ResponseEntity<UserLinkListResponse> getUserLinkList(
            @RequestParam(value = "linkId", required = false) Long lastLinkId,
            @PageableDefault Pageable pageable,
            AuthInfo authInfo
    ) {
        UserLinkListResponse userLinkListResponse = linkQueryService.getUserLinkList(authInfo.getId(), pageable, lastLinkId);
        return ResponseEntity.ok(userLinkListResponse);
    }

    // 다른 사용자 링크 리스트 조회 - 로그인 필요 X
    @GetMapping("/links/user/{userId}")
    public ResponseEntity<UserLinkListResponse> getPublicUserLinkList(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "linkId", required = false) Long lastLinkId,
            @PageableDefault Pageable pageable
    ) {
        UserLinkListResponse userLinkListResponse = linkQueryService.getPublicUserLinkList(userId, pageable, lastLinkId);
        return ResponseEntity.ok(userLinkListResponse);
    }

    // 다른 사용자 링크 리스트 조회 - 로그인 필요 O
    @GetMapping("/links/user/{userId}")
    public ResponseEntity<UserLinkListResponse> getAuthenticatedUserLinkList(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "linkId", required = false) Long lastLinkId,
            @PageableDefault Pageable pageable,
            AuthInfo authInfo
    ) {
        UserLinkListResponse userLinkListResponse = linkQueryService.getAuthenticatedUserLinkList(userId, pageable, lastLinkId, authInfo.getId());
        return ResponseEntity.ok(userLinkListResponse);
    }

    // 사용자들의 링크 리스트 조회 - 로그인 필요 X
    @GetMapping("/links/archive")
    public ResponseEntity<UserLinkArchiveResponse> getPublicLinkArchive(
            @PageableDefault Pageable pageable,
            @RequestParam(value = "linkId", required = false) Long lastLinkId
    ) {
        UserLinkArchiveResponse userLinkArchiveResponse = linkQueryService.getPublicLinkArchive(pageable, lastLinkId);
        return ResponseEntity.ok(userLinkArchiveResponse);
    }

    // 사용자들의 링크 리스트 조회 - 로그인 필요 X
    @GetMapping("/links/archive")
    public ResponseEntity<UserLinkArchiveResponse> getAuthenticatedLinkArchive(
            @PageableDefault Pageable pageable,
            @RequestParam(value = "linkId", required = false) Long lastLinkId,
            AuthInfo authInfo
    ) {
        UserLinkArchiveResponse userLinkArchiveResponse = linkQueryService.getAuthenticatedLinkArchive(pageable, lastLinkId, authInfo.getId());
        return ResponseEntity.ok(userLinkArchiveResponse);
    }

}