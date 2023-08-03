package project.linkarchive.backend.bookmark.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.bookmark.response.UserMarkListResponse;
import project.linkarchive.backend.bookmark.service.BookMarkQueryService;
import project.linkarchive.backend.hashtag.response.TagListResponse;
import project.linkarchive.backend.security.AuthInfo;

@RestController
public class BookMarkQueryController {

    private final BookMarkQueryService bookMarkQueryService;

    public BookMarkQueryController(BookMarkQueryService bookMarkQueryService) {
        this.bookMarkQueryService = bookMarkQueryService;
    }

    @GetMapping("/mark/links/user")
    public ResponseEntity<UserMarkListResponse> getMyMarkLinkList(
            @RequestParam(value = "tagId", required = false) Long tagId,
            @RequestParam(value = "markId", required = false) Long markId,
            @PageableDefault Pageable pageable,
            AuthInfo authInfo
    ) {
        UserMarkListResponse userMarkListResponse = bookMarkQueryService.getMyMarkedLinkList(tagId, markId, pageable, authInfo.getId());
        return ResponseEntity.ok(userMarkListResponse);
    }

    @GetMapping("/mark/links/user/{userId}")
    public ResponseEntity<UserMarkListResponse> getUserMarkedLinkList(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "tagId", required = false) Long tagId,
            @RequestParam(value = "markId", required = false) Long markId,
            @PageableDefault Pageable pageable,
            @Nullable AuthInfo authInfo
    ) {
        UserMarkListResponse userMarkListResponse = bookMarkQueryService.getUserMarkedLinkList(userId, tagId, markId, pageable, authInfo);
        return ResponseEntity.ok(userMarkListResponse);
    }

    @GetMapping("/mark/tags/user/{userId}")
    public ResponseEntity<TagListResponse> getMarkTagList(
            @PathVariable("userId") Long userId
    ) {
        TagListResponse tagList = bookMarkQueryService.getMarkTagList(userId);
        return ResponseEntity.ok(tagList);
    }

    @GetMapping("/mark/tags/10/user/{userId}")
    public ResponseEntity<TagListResponse> getMarkTagList10(
            @PathVariable("userId") Long userId
    ) {
        TagListResponse tagList = bookMarkQueryService.getMarkTagList10(userId);
        return ResponseEntity.ok(tagList);
    }

}