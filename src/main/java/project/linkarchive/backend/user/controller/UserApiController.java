package project.linkarchive.backend.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.linkarchive.backend.advice.success.SuccessResponse;
import project.linkarchive.backend.profileImage.response.ProfileImageResponse;
import project.linkarchive.backend.security.AuthInfo;
import project.linkarchive.backend.user.request.UpdateNicknameRequest;
import project.linkarchive.backend.user.request.UpdateProfileRequest;
import project.linkarchive.backend.user.response.UpdateNicknameResponse;
import project.linkarchive.backend.user.response.UpdateProfileResponse;
import project.linkarchive.backend.user.service.UserApiService;

import java.io.IOException;

import static org.springframework.http.HttpStatus.CREATED;
import static project.linkarchive.backend.advice.success.SuccessCodeConst.AVAILABLE_NICKNAME;

@RestController
public class UserApiController {

    private final UserApiService userApiService;

    public UserApiController(UserApiService userApiService) {
        this.userApiService = userApiService;
    }

    @PatchMapping("/user/{userId}/nickname")
    public ResponseEntity<UpdateNicknameResponse> updateUserNickname(
            @PathVariable("userId") Long userId,
            @RequestBody UpdateNicknameRequest request
    ) {
        UpdateNicknameResponse updateNicknameResponse = userApiService.updateUserNickName(request, userId);
        return ResponseEntity.ok(updateNicknameResponse);
    }

    @PatchMapping("/user")
    public ResponseEntity<UpdateProfileResponse> updateUserProfile(
            @RequestBody UpdateProfileRequest request,
            AuthInfo authInfo
    ) {
        UpdateProfileResponse updateProfileResponse = userApiService.updateUserProfile(request, authInfo.getId());
        return ResponseEntity.ok(updateProfileResponse);
    }

    @PatchMapping("/profile-image")
    public ResponseEntity<ProfileImageResponse> updateProfileImage(
            @RequestParam(value = "image") MultipartFile profileImage,
            AuthInfo authInfo
    ) throws IOException {
        ProfileImageResponse profileImageResponse = userApiService.updateProfileImage(profileImage, authInfo.getId());
        return ResponseEntity.ok().body(profileImageResponse);
    }

    @PostMapping("/nickname")
    public ResponseEntity<SuccessResponse> validationNickName(
            @RequestBody UpdateNicknameRequest request
    ) {
        userApiService.validateNickName(request);
        return ResponseEntity.status(CREATED).body(new SuccessResponse(AVAILABLE_NICKNAME));
    }

}