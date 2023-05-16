package project.linkarchive.backend.bookmark.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.bookmark.service.BookMarkQueryService;
import project.linkarchive.backend.url.response.RefactorUserLinkList.UserLinkListResponse;

@RestController
@RequiredArgsConstructor
public class BookMarkQueryController {

    private final BookMarkQueryService bookMarkQueryService;

    @GetMapping("/mark/links/user/{userId}")
    public ResponseEntity<UserLinkListResponse> getMarkedLinkList(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "urlId", required = false) Long lastUrlId,
            @PageableDefault Pageable pageable
    ) {

        UserLinkListResponse userLinkListResponse = bookMarkQueryService.getMarkedLinkList(userId, pageable, lastUrlId);
        return ResponseEntity.ok(userLinkListResponse);

    }
}