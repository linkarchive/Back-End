package project.linkarchive.backend.bookmark.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.advice.success.SuccessResponse;
import project.linkarchive.backend.bookmark.service.BookMarkApiService;

import static project.linkarchive.backend.advice.success.SuccessCodeConst.BOOK_MARK;
import static project.linkarchive.backend.advice.success.SuccessCodeConst.BOOK_MARK_CANCEL;

@RestController
public class BookMarkApiController {

    private final BookMarkApiService bookMarkApiService;

    public BookMarkApiController(BookMarkApiService bookMarkApiService) {
        this.bookMarkApiService = bookMarkApiService;
    }

    //FIXME 유저에 관한 정보가 없어요.
    @PostMapping("/mark/url/{urlId}")
    public ResponseEntity<SuccessResponse> bookMark(@PathVariable(value = "urlId") Long urlId) {
        bookMarkApiService.bookMark(urlId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(BOOK_MARK));
    }

    //FIXME 유저에 관한 정보가 없어요.
    @DeleteMapping("/mark/url/{urlId}")
    public ResponseEntity<SuccessResponse> bookMarkCancel(@PathVariable(value = "urlId") Long urlId) {

        return ResponseEntity.ok(new SuccessResponse(BOOK_MARK_CANCEL));
    }

}