package project.linkarchive.backend.bookmark.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.bookmark.service.BookMarkQueryService;
import project.linkarchive.backend.link.response.RefactorUserLinkList.UserLinkListResponse;

@RestController
public class BookMarkQueryController {

    private final BookMarkQueryService bookMarkQueryService;

    public BookMarkQueryController(BookMarkQueryService bookMarkQueryService) {
        this.bookMarkQueryService = bookMarkQueryService;
    }

    @GetMapping("/mark/links/user/{userId}")
    public ResponseEntity<UserLinkListResponse> getMarkedLinkList(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "urlId", required = false) Long lastUrlId,
            @PageableDefault Pageable pageable) {
        UserLinkListResponse userLinkListResponse = bookMarkQueryService.getMarkedLinkList(userId, lastUrlId, pageable);
        return ResponseEntity.ok(userLinkListResponse);
    }

}