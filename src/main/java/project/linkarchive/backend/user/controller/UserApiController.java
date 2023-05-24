package project.linkarchive.backend.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.linkarchive.backend.advice.success.SuccessResponse;
import project.linkarchive.backend.security.AuthInfo;
import project.linkarchive.backend.user.request.UpdateNickNameRequest;
import project.linkarchive.backend.user.request.UpdateProfileRequest;
import project.linkarchive.backend.user.response.ProfileImageResponse;
import project.linkarchive.backend.user.service.UserApiService;

import java.io.IOException;

import static project.linkarchive.backend.advice.success.SuccessCodeConst.*;

@RestController
public class UserApiController {

    private final UserApiService userApiService;

    public UserApiController(UserApiService userApiService) {
        this.userApiService = userApiService;
    }

    @PatchMapping("/user")
    public ResponseEntity<SuccessResponse> updateUserProfile(
            @RequestBody UpdateProfileRequest request,
            AuthInfo authInfo
    ) {
        userApiService.updateUserProfile(request, authInfo.getId());
        return ResponseEntity.ok(new SuccessResponse(UPDATE_USER_PROFILE));
    }

    @PatchMapping("/user/{userId}/nickname")
    public ResponseEntity<SuccessResponse> updateUserNickname(
            @PathVariable("userId") Long userId,
            @RequestBody UpdateNickNameRequest request) {
        userApiService.updateUserNickName(request, userId);
        return ResponseEntity.ok(new SuccessResponse(UPDATE_NICKNAME));
    }

    @PatchMapping("/profile-image")
    public ResponseEntity<ProfileImageResponse> updateProfileImage(
            @RequestParam(value = "image") MultipartFile image,
            AuthInfo authInfo
    ) throws IOException {
        ProfileImageResponse profileImageResponse = userApiService.saveProfileImage(image, authInfo.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(profileImageResponse);
    }

}