package project.linkarchive.backend.bookmark.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.bookmark.service.BookMarkQueryService;
import project.linkarchive.backend.hashtag.response.TagListResponse;
import project.linkarchive.backend.link.response.linkList.UserLinkListResponse;
import project.linkarchive.backend.security.AuthInfo;

@RestController
public class BookMarkQueryController {

    private final BookMarkQueryService bookMarkQueryService;

    public BookMarkQueryController(BookMarkQueryService bookMarkQueryService) {
        this.bookMarkQueryService = bookMarkQueryService;
    }

    // 내 북마크 리스트 조회 - 로그인 필요 O
    @GetMapping("/mark/links/user")
    public ResponseEntity<UserLinkListResponse> getUserMarkedLinkList(
            @RequestParam(value = "linkId", required = false) Long lastLinkId,
            @PageableDefault Pageable pageable,
            AuthInfo authInfo
    ) {
        UserLinkListResponse userLinkListResponse = bookMarkQueryService.getUserMarkedLinkList(authInfo.getId(), lastLinkId, pageable);
        return ResponseEntity.ok(userLinkListResponse);
    }

    // 사용자별 북마크 리스트 조회 - 로그인 필요 X
    @GetMapping("/mark/links/public/user/{userId}")
    public ResponseEntity<UserLinkListResponse> getPublicUserMarkedLinkList(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "linkId", required = false) Long lastLinkId,
            @PageableDefault Pageable pageable
    ) {
        UserLinkListResponse userLinkListResponse = bookMarkQueryService.getPublicUserMarkedLinkList(userId, lastLinkId, pageable);
        return ResponseEntity.ok(userLinkListResponse);
    }

    // 사용자별 북마크 리스트 조회 - 로그인 필요 O
    @GetMapping("/mark/links/authentication/user/{userId}")
    public ResponseEntity<UserLinkListResponse> getAuthenticatedUserMarkedLinkList(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "linkId", required = false) Long lastLinkId,
            @PageableDefault Pageable pageable,
            AuthInfo authInfo
    ) {
        UserLinkListResponse userLinkListResponse = bookMarkQueryService.getAuthenticatedUserMarkedLinkList(userId, lastLinkId, pageable, authInfo.getId());
        return ResponseEntity.ok(userLinkListResponse);
    }

    // 사용자 별 북마크 리스트 중 해시태그 리스트 조회 011
    @GetMapping("/marks/tags/user/{userId}")
    public ResponseEntity<TagListResponse> getMarkTagList(
            @PathVariable("userId") Long userId
    ) {
        TagListResponse tagList = bookMarkQueryService.getMarkTagList(userId);
        return ResponseEntity.ok(tagList);
    }

    // 사용자 별 북마크 리스트 중 해시태그 N개 조회 009
    @GetMapping("/marks/limited-tags/user/{userId}")
    public ResponseEntity<TagListResponse> getMarkTagLimitList(
            @PathVariable("userId") Long userId,
            @RequestParam("size") Long size
    ) {
        TagListResponse tagList = bookMarkQueryService.getMarkTagLimitList(userId, size);
        return ResponseEntity.ok(tagList);
    }

}