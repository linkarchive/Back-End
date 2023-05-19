package project.linkarchive.backend.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import project.linkarchive.backend.advice.success.SuccessCodeConst;
import project.linkarchive.backend.advice.success.SuccessResponse;
import project.linkarchive.backend.security.AuthInfo;
import project.linkarchive.backend.user.service.ProfileService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping(value = "/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse> saveImage(
            AuthInfo authInfo,
            @RequestParam(value = "image") MultipartFile image
    ) throws IOException {
        profileService.saveProfileImage(image, authInfo.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(SuccessCodeConst.UPDATE_PROFILE_IMAGE));
    }
}






