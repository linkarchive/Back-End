package project.linkarchive.backend.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.security.AuthInfo;
import project.linkarchive.backend.user.response.ProfileResponse;
import project.linkarchive.backend.user.service.UserQueryService;

@RestController
public class UserQueryController {

    private final UserQueryService userQueryService;

    public UserQueryController(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @GetMapping("/user")
    public ResponseEntity<ProfileResponse> getProfile(
            AuthInfo authInfo
    ) {
        ProfileResponse profileResponse = userQueryService.getMyProfile(authInfo.getId());
        return ResponseEntity.ok(profileResponse);
    }

    @GetMapping("/user/{nickname}")
    public ResponseEntity<ProfileResponse> getUserProfile(
            @PathVariable("nickname") String nickname,
            @Nullable AuthInfo authInfo
    ) {
        ProfileResponse profileResponse = userQueryService.getUserProfile(nickname, authInfo);
        return ResponseEntity.ok(profileResponse);
    }

}