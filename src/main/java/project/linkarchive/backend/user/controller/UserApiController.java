package project.linkarchive.backend.user.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.linkarchive.backend.security.AuthInfo;

@RestController
@PreAuthorize("isAuthenticated()")
public class UserApiController {

    @GetMapping("/user")
    public AuthInfo getUserProfile(
            AuthInfo authInfo
    ) {
        return authInfo;
    }

}
