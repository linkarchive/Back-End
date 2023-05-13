package project.linkarchive.backend.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.security.AuthInfo;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.response.UserProfileResponse;
import project.linkarchive.backend.user.service.UserApiService;

@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class UserApiController {

    private final UserApiService userApiService;

    @GetMapping("/user")
    public ResponseEntity<UserProfileResponse> getUserProfile(
            AuthInfo authInfo
    ) {
        User user = userApiService.getUserProfile(authInfo.getId());
        UserProfileResponse userProfileResponse = new UserProfileResponse(user);
        return ResponseEntity.ok(userProfileResponse);
    }

}
