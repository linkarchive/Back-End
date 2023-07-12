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
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "markId", required = false) Long lastMarkId,
            @PageableDefault Pageable pageable,
            AuthInfo authInfo
    ) {
        UserMarkListResponse userMarkListResponse = bookMarkQueryService.getMyMarkedLinkList(authInfo.getId(), lastMarkId, pageable, tag);
        return ResponseEntity.ok(userMarkListResponse);
    }

    @GetMapping("/mark/links/user/{nickname}")
    public ResponseEntity<UserMarkListResponse> getAuthenticatedUserMarkedLinkList(
            @PathVariable("nickname") String nickname,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "markId", required = false) Long lastMarkId,
            @PageableDefault Pageable pageable,
            @Nullable AuthInfo authInfo
    ) {
        UserMarkListResponse userMarkListResponse = bookMarkQueryService.getUserMarkedLinkList(nickname, lastMarkId, pageable, authInfo, tag);
        return ResponseEntity.ok(userMarkListResponse);
    }

    @GetMapping("/mark/tags/user/{nickname}")
    public ResponseEntity<TagListResponse> getMarkTagList(
            @PathVariable("nickname") String nickname
    ) {
        TagListResponse tagList = bookMarkQueryService.getMarkTagList(nickname);
        return ResponseEntity.ok(tagList);
    }

    @GetMapping("/mark/limited-tags/user/{nickname}")
    public ResponseEntity<TagListResponse> getMarkTagLimitList(
            @PathVariable("nickname") String nickname,
            @RequestParam("size") int size
    ) {
        TagListResponse tagList = bookMarkQueryService.getMarkTagLimitList(nickname, size);
        return ResponseEntity.ok(tagList);
    }

}