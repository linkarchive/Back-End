package project.linkarchive.backend.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.advice.success.SuccessResponse;
import project.linkarchive.backend.security.AuthInfo;
import project.linkarchive.backend.user.request.NickNameRequest;
import project.linkarchive.backend.user.response.ProfileResponse;
import project.linkarchive.backend.user.service.UserQueryService;

import static project.linkarchive.backend.advice.success.SuccessCodeConst.AVAILABLE_NICKNAME;

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
        ProfileResponse profileResponse = userQueryService.getUserProfile(authInfo.getId());
        return ResponseEntity.ok(profileResponse);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ProfileResponse> getUserProfile(
            @PathVariable(value = "userId") Long userId
    ) {
        ProfileResponse profileResponse = userQueryService.getUserProfile(userId);
        return ResponseEntity.ok(profileResponse);
    }

}