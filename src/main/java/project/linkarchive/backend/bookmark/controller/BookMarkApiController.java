package project.linkarchive.backend.bookmark.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.advice.success.SuccessResponse;
import project.linkarchive.backend.bookmark.service.BookMarkApiService;
import project.linkarchive.backend.security.AuthInfo;

import static project.linkarchive.backend.advice.success.SuccessCodeConst.BOOK_MARK;
import static project.linkarchive.backend.advice.success.SuccessCodeConst.BOOK_MARK_CANCEL;

@RestController
public class BookMarkApiController {

    private final BookMarkApiService bookMarkApiService;

    public BookMarkApiController(BookMarkApiService bookMarkApiService) {
        this.bookMarkApiService = bookMarkApiService;
    }

    @PostMapping("/mark/link/{linkId}")
    public ResponseEntity<SuccessResponse> bookmark(
            @PathVariable(value = "linkId") Long linkId,
            AuthInfo authInfo
    ) {
        bookMarkApiService.bookmark(linkId, authInfo.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(BOOK_MARK));
    }

    @DeleteMapping("/mark/link/{linkId}")
    public ResponseEntity<SuccessResponse> bookmarkCancel(
            @PathVariable(value = "linkId") Long linkId,
            AuthInfo authInfo
    ) {
        bookMarkApiService.bookmarkCancel(linkId, authInfo.getId());
        return ResponseEntity.ok(new SuccessResponse(BOOK_MARK_CANCEL));
    }

}