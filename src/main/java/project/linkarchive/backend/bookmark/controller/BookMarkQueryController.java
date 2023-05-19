package project.linkarchive.backend.bookmark.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.bookmark.service.BookMarkQueryService;
import project.linkarchive.backend.hashtag.response.TagListResponse;
import project.linkarchive.backend.hashtag.response.TagResponse;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class BookMarkQueryController {

    private final BookMarkQueryService bookMarkQueryService;

    public BookMarkQueryController(BookMarkQueryService bookMarkQueryService) {
        this.bookMarkQueryService = bookMarkQueryService;
    }

    @GetMapping("/mark/tags/user/{userId}")
    public ResponseEntity<TagListResponse> getMarkTagList(@PathVariable("userId") Long userId) {
        List<TagResponse> tagList = bookMarkQueryService.getMarkTagList(userId);
        return ResponseEntity.ok(new TagListResponse(tagList));
    }

}