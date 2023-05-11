package project.linkarchive.backend.bookmark.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.bookmark.response.UserMarkedLinkListResponse;
import project.linkarchive.backend.bookmark.service.BookMarkQueryService;

@RestController
public class BookMarkQueryController {

    private final BookMarkQueryService bookMarkQueryService;

    public BookMarkQueryController(BookMarkQueryService bookMarkQueryService) {
        this.bookMarkQueryService = bookMarkQueryService;
    }

    @GetMapping("/links/mark")
    public ResponseEntity<UserMarkedLinkListResponse> getMarkLinkList(@PageableDefault(direction = Sort.Direction.DESC) Pageable pageable,
                                                                      @RequestParam(value = "urlId", required = false) Long lastUrlId) {
        UserMarkedLinkListResponse userMarkedLinkListResponse = bookMarkQueryService.getMarkLinkList(pageable, lastUrlId);
        return ResponseEntity.ok(userMarkedLinkListResponse);
    }

}