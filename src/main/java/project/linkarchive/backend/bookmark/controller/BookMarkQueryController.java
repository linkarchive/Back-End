package project.linkarchive.backend.bookmark.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.bookmark.service.BookMarkQueryService;
import project.linkarchive.backend.hashtag.response.TagListResponse;
import project.linkarchive.backend.link.response.linkList.UserLinkListResponse;

@RestController
@PreAuthorize("isAuthenticated()")
public class BookMarkQueryController {

    private final BookMarkQueryService bookMarkQueryService;

    public BookMarkQueryController(BookMarkQueryService bookMarkQueryService) {
        this.bookMarkQueryService = bookMarkQueryService;
    }

    // 사용자별 북마크 리스트 조회 010
    @GetMapping("/mark/links/user/{userId}")
    public ResponseEntity<UserLinkListResponse> getMarkedLinkList(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "linkId", required = false) Long lastLinkId,
            @PageableDefault Pageable pageable
    ) {
        UserLinkListResponse userLinkListResponse = bookMarkQueryService.getMarkedLinkList(userId, lastLinkId, pageable);
        return ResponseEntity.ok(userLinkListResponse);
    }

    // 사용자 별 북마크 리스트 중 해시태그 리스트 조회 011
    @GetMapping("/mark/tags/user/{userId}")
    public ResponseEntity<TagListResponse> getMarkTagList(@PathVariable("userId") Long userId) {
        TagListResponse tagList = bookMarkQueryService.getMarkTagList(userId);
        return ResponseEntity.ok(tagList);
    }

    // 사용자 별 북마크 리스트 중 해시태그 N개 조회 009
    @GetMapping("/mark/tags/limit/user/{userId}")
    public ResponseEntity<TagListResponse> getMarkTagLimitList(@PathVariable("userId") Long userId,
                                                               @RequestParam("size") Long size) {
        TagListResponse tagList = bookMarkQueryService.getMarkTagLimitList(userId, size);
        return ResponseEntity.ok(tagList);
    }

}