package project.linkarchive.backend.link.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.link.response.LinkMetaDataResponse;
import project.linkarchive.backend.link.response.linkList.UserLinkListResponse;
import project.linkarchive.backend.link.response.linkarchive.UserLinkArchiveResponse;
import project.linkarchive.backend.link.response.trash.UserTrashLinkListResponse;
import project.linkarchive.backend.link.service.LinkQueryService;
import project.linkarchive.backend.security.AuthInfo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.springframework.http.HttpStatus.*;
import static project.linkarchive.backend.advice.data.DataConstants.EMPTY;

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
            return ResponseEntity.status(BAD_REQUEST).build();
        }

        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) urlObject.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                return ResponseEntity.status(NOT_FOUND).build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }

        Document document;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }

        String metaTitle = document.select("meta[property=og:title]").attr("content");
        if (metaTitle.equals(EMPTY)) {
            metaTitle = document.select("title").text();
        }
        String metaDescription = document.select("meta[property=og:description]").attr("content");
        String metaThumbnail = document.select("meta[property=og:image]").attr("content");

        LinkMetaDataResponse linkMetaDataResponse = new LinkMetaDataResponse(metaTitle, metaDescription, metaThumbnail);
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

    @GetMapping("/links/user/{nickname}")
    public ResponseEntity<UserLinkListResponse> getUserLinkList(
            @PathVariable("nickname") String nickname,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "linkId", required = false) Long lastLinkId,
            @PageableDefault Pageable pageable,
            @Nullable AuthInfo authInfo
    ) {
        UserLinkListResponse userLinkListResponse = linkQueryService.getUserLinkList(nickname, pageable, lastLinkId, authInfo, tag);
        return ResponseEntity.ok(userLinkListResponse);
    }

    @GetMapping("/links/archive")
    public ResponseEntity<UserLinkArchiveResponse> getLinkArchive(
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "linkId", required = false) Long lastLinkId,
            @PageableDefault Pageable pageable,
            @Nullable AuthInfo authInfo
    ) {
        UserLinkArchiveResponse userLinkArchiveResponse = linkQueryService.getLinkArchive(pageable, lastLinkId, authInfo, tag);
        return ResponseEntity.ok(userLinkArchiveResponse);
    }

    @GetMapping("/links/trash")
    public ResponseEntity<UserTrashLinkListResponse> getLinkTrashList(
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "linkId", required = false) Long lastLinkId,
            @PageableDefault Pageable pageable,
            AuthInfo authInfo
    ) {
        UserTrashLinkListResponse userTrashLinkListResponse = linkQueryService.getTrashLinkList(tag, lastLinkId, pageable, authInfo.getId());
        return ResponseEntity.ok(userTrashLinkListResponse);
    }

}