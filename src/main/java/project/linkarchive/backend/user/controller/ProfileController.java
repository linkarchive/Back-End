package project.linkarchive.backend.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import project.linkarchive.backend.advice.success.SuccessResponse;
import project.linkarchive.backend.security.AuthInfo;
import project.linkarchive.backend.user.service.ProfileImageService;

import java.io.IOException;

import static project.linkarchive.backend.advice.success.SuccessCodeConst.UPDATE_PROFILE_IMAGE;

@RestController
@PreAuthorize("isAuthenticated()")
public class ProfileController {

    private final ProfileImageService profileImageService;

    public ProfileController(ProfileImageService profileImageService) {
        this.profileImageService = profileImageService;
    }

    @PatchMapping(value = "/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse> updateProfileImage(
            @RequestParam(value = "image") MultipartFile image,
            AuthInfo authInfo
    ) throws IOException {
        profileImageService.saveProfileImage(image, authInfo.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(UPDATE_PROFILE_IMAGE));
    }

}