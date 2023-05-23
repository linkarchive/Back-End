package project.linkarchive.backend.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import project.linkarchive.backend.advice.success.SuccessResponse;
import project.linkarchive.backend.security.AuthInfo;
import project.linkarchive.backend.user.request.UpdateNickNameRequest;
import project.linkarchive.backend.user.request.UpdateProfileRequest;
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

    @PatchMapping("/user/nickname")
    public ResponseEntity<SuccessResponse> updateUserNickname(
            @RequestBody UpdateNickNameRequest request,
            AuthInfo authInfo
    ) {
        userApiService.updateUserNickName(request, authInfo.getId());
        return ResponseEntity.ok(new SuccessResponse(UPDATE_NICKNAME));
    }

    @PatchMapping("/profile-image")
    public ResponseEntity<SuccessResponse> updateProfileImage(
            @RequestParam(value = "image") MultipartFile image,
            AuthInfo authInfo
    ) throws IOException {
        userApiService.saveProfileImage(image, authInfo.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(UPDATE_PROFILE_IMAGE));
    }

}