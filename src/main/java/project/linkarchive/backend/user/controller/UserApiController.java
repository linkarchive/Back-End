package project.linkarchive.backend.user.controller;

import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("isAuthenticated()")
public class UserApiController {
}
