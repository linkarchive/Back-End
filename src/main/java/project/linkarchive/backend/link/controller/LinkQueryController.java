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
    public ResponseEntity<LinkMetaDataResponse> getUrlMetaData(@RequestParam(value = "url") String url) {
        URL urlObject;
        try {
            urlObject = new URL(url);
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
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        String titleText = document.select("title").text();
        String metaTitle = document.select("meta[property=og:title]").attr("content");
        String metaDescription = document.select("meta[property=og:description]").attr("content");
        String metaThumbnail = document.select("meta[property=og:image]").attr("content");

        LinkMetaDataResponse linkMetaDataResponse = new LinkMetaDataResponse(titleText, metaTitle, metaDescription, metaThumbnail);
        return ResponseEntity.ok(linkMetaDataResponse);
    }

    @GetMapping("/links/user")
    public ResponseEntity<UserLinkListResponse> getMyLinkList(
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "linkId", required = false) Long lastLinkId,
            @PageableDefault Pageable pageable,
            AuthInfo authInfo
    ) {
        UserLinkListResponse userLinkListResponse = linkQueryService.getMyLinkList(authInfo.getId(), pageable, lastLinkId, tag);
        return ResponseEntity.ok(userLinkListResponse);
    }

    @GetMapping("/links/public/user/{nickname}")
    public ResponseEntity<UserLinkListResponse> getPublicUserLinkList(
            @PathVariable("nickname") String nickname,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "linkId", required = false) Long lastLinkId,
            @PageableDefault Pageable pageable
    ) {
        UserLinkListResponse userLinkListResponse = linkQueryService.getPublicUserLinkList(nickname, pageable, lastLinkId, tag);
        return ResponseEntity.ok(userLinkListResponse);
    }

    @GetMapping("/links/authentication/user/{nickname}")
    public ResponseEntity<UserLinkListResponse> getAuthenticatedUserLinkList(
            @PathVariable("nickname") String nickname,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "linkId", required = false) Long lastLinkId,
            @PageableDefault Pageable pageable,
            AuthInfo authInfo
    ) {
        UserLinkListResponse userLinkListResponse = linkQueryService.getAuthenticatedUserLinkList(nickname, pageable, lastLinkId, authInfo.getId(), tag);
        return ResponseEntity.ok(userLinkListResponse);
    }

    @GetMapping("/links/archive/public")
    public ResponseEntity<UserLinkArchiveResponse> getPublicLinkArchive(
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "linkId", required = false) Long lastLinkId,
            @PageableDefault Pageable pageable
    ) {
        UserLinkArchiveResponse userLinkArchiveResponse = linkQueryService.getPublicLinkArchive(pageable, lastLinkId, tag);
        return ResponseEntity.ok(userLinkArchiveResponse);
    }

    @GetMapping("/links/archive/authentication")
    public ResponseEntity<UserLinkArchiveResponse> getAuthenticatedLinkArchive(
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "linkId", required = false) Long lastLinkId,
            @PageableDefault Pageable pageable,
            AuthInfo authInfo
    ) {
        UserLinkArchiveResponse userLinkArchiveResponse = linkQueryService.getAuthenticatedLinkArchive(pageable, lastLinkId, authInfo.getId(), tag);
        return ResponseEntity.ok(userLinkArchiveResponse);
    }

}