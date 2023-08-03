package project.linkarchive.backend.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.security.AuthInfo;
import project.linkarchive.backend.user.response.MyProfileResponse;
import project.linkarchive.backend.user.response.UserProfileResponse;
import project.linkarchive.backend.user.service.UserQueryService;

@RestController
public class UserQueryController {

    private final UserQueryService userQueryService;

    public UserQueryController(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @GetMapping("/user")
    public ResponseEntity<MyProfileResponse> getProfile(
            AuthInfo authInfo
    ) {
        MyProfileResponse myProfileResponse = userQueryService.getMyProfile(authInfo.getId());
        return ResponseEntity.ok(myProfileResponse);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserProfileResponse> getUserProfile(
            @PathVariable("userId") Long userId,
            @Nullable AuthInfo authInfo
    ) {
        UserProfileResponse userProfileResponse = userQueryService.getUserProfile(userId, authInfo);
        return ResponseEntity.ok(userProfileResponse);
    }

}