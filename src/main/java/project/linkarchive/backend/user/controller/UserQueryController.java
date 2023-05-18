package project.linkarchive.backend.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.user.response.ProfileResponse;
import project.linkarchive.backend.user.service.UserQueryService;

@RestController
@PreAuthorize("isAuthenticated()")
public class UserQueryController {

    private final UserQueryService userQueryService;

    public UserQueryController(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ProfileResponse> getUserProfile(
            @PathVariable(value = "userId") Long userId
    ) {
        ProfileResponse profileResponse = userQueryService.getUserProfile(userId);
        return ResponseEntity.ok(profileResponse);
    }
    
}
