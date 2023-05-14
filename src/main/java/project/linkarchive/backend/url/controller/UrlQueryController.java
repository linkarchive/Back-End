package project.linkarchive.backend.url.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.security.AuthInfo;
import project.linkarchive.backend.url.response.UrlMetaDataResponse;
import project.linkarchive.backend.url.response.linkList.UserExcludedLinkListResponse;
import project.linkarchive.backend.url.response.otherUserLinkList.OtherUserLinkListResponse;
import project.linkarchive.backend.url.response.otherUserLinkList.OtherUserUrlListResponse;
import project.linkarchive.backend.url.response.userLinkList.UserLinkListResponse;
import project.linkarchive.backend.url.service.UrlQueryService;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class UrlQueryController {

    private final UrlQueryService urlQueryService;

    public UrlQueryController(UrlQueryService urlQueryService) {
        this.urlQueryService = urlQueryService;
    }

    @GetMapping("/link/metadata")
    public ResponseEntity<UrlMetaDataResponse> getUrlMetaData(@RequestParam(value = "url") String url) {
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

        String title = document.select("meta[property=og:title]").attr("content");
        String description = document.select("meta[property=og:description]").attr("content");
        String thumbnail = document.select("meta[property=og:image]").attr("content");

        UrlMetaDataResponse urlMetaDataResponse = new UrlMetaDataResponse(title, description, thumbnail);
        return ResponseEntity.ok(urlMetaDataResponse);
    }


    @GetMapping("/links")
    public ResponseEntity<UserLinkListResponse> getUserLinkList(@PageableDefault(direction = Sort.Direction.DESC) Pageable pageable,
                                                                @RequestParam(value = "urlId", required = false) Long lastUrlId,
                                                                AuthInfo authInfo) {
        UserLinkListResponse userLinkListResponse = urlQueryService.getUserLinkList(pageable, lastUrlId, authInfo.getId());
        return ResponseEntity.ok(userLinkListResponse);
    }

    @GetMapping("/links/archive")
    public ResponseEntity<UserExcludedLinkListResponse> getLinkList(@PageableDefault(direction = Sort.Direction.DESC) Pageable pageable,
                                                                    @RequestParam(value = "urlId", required = false) Long lastUrlId) {
        UserExcludedLinkListResponse userExcludedLinkListResponse = urlQueryService.getLinkList(pageable, lastUrlId);
        return ResponseEntity.ok(userExcludedLinkListResponse);
    }

    @GetMapping("/links/user")
    public ResponseEntity<OtherUserLinkListResponse> getOtherUserLinkList(
            @RequestParam(value = "userId") Long userId
    ) {
        OtherUserLinkListResponse otherUserLinkListResponse = urlQueryService.getOtherUserLinkList(userId);
        return ResponseEntity.ok(otherUserLinkListResponse);
    }
}


