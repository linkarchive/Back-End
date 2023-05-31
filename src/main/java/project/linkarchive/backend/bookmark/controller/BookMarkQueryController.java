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

    @GetMapping("/mark/links/user")
    public ResponseEntity<UserLinkListResponse> getUserMarkedLinkList(
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "linkId", required = false) Long lastLinkId,
            @PageableDefault Pageable pageable,
            AuthInfo authInfo
    ) {
        UserLinkListResponse userLinkListResponse = bookMarkQueryService.getUserMarkedLinkList(authInfo.getId(), lastLinkId, pageable, tag);
        return ResponseEntity.ok(userLinkListResponse);
    }

    @GetMapping("/mark/links/public/user/{nickname}")
    public ResponseEntity<UserLinkListResponse> getPublicUserMarkedLinkList(
            @PathVariable("nickname") String nickname,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "linkId", required = false) Long lastLinkId,
            @PageableDefault Pageable pageable
    ) {
        UserLinkListResponse userLinkListResponse = bookMarkQueryService.getPublicUserMarkedLinkList(nickname, lastLinkId, pageable, tag);
        return ResponseEntity.ok(userLinkListResponse);
    }

    @GetMapping("/mark/links/authentication/user/{nickname}")
    public ResponseEntity<UserLinkListResponse> getAuthenticatedUserMarkedLinkList(
            @PathVariable("nickname") String nickname,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "linkId", required = false) Long lastLinkId,
            @PageableDefault Pageable pageable,
            AuthInfo authInfo
    ) {
        UserLinkListResponse userLinkListResponse = bookMarkQueryService.getAuthenticatedUserMarkedLinkList(nickname, lastLinkId, pageable, authInfo.getId(), tag);
        return ResponseEntity.ok(userLinkListResponse);
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
            @RequestParam("size") Long size
    ) {
        TagListResponse tagList = bookMarkQueryService.getMarkTagLimitList(nickname, size);
        return ResponseEntity.ok(tagList);
    }

}