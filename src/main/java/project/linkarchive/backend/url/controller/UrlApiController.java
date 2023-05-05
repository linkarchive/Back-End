package project.linkarchive.backend.url.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.linkarchive.backend.advice.success.SuccessCodeConst;
import project.linkarchive.backend.advice.success.SuccessResponse;
import project.linkarchive.backend.url.request.UrlCreateRequest;
import project.linkarchive.backend.url.response.UrlMetaDataResponse;
import project.linkarchive.backend.url.service.UrlApiService;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
public class UrlApiController {

    private final UrlApiService urlApiService;

    public UrlApiController(UrlApiService urlApiService) {
        this.urlApiService = urlApiService;
    }

    @PostMapping("/url")
    public ResponseEntity<SuccessResponse> create(@RequestBody UrlCreateRequest request) {
        urlApiService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(SuccessCodeConst.URL_CREATE));
    }

    @GetMapping("/url/metadata")
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

}