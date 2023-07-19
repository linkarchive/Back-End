package project.linkarchive.backend.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;
import project.linkarchive.backend.profileImage.response.ProfileImageResponse;
import project.linkarchive.backend.user.response.UpdateNicknameResponse;
import project.linkarchive.backend.user.response.UpdateProfileResponse;
import project.linkarchive.backend.util.service.UserSetUpService;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static project.linkarchive.backend.util.constant.Constants.*;

class UserApiServiceTest extends UserSetUpService {

    @BeforeEach
    public void setUp() {
        setUpUser();
        setUpProfileImage();
        setUpMultipartFile();
        setUpUpdateNicknameRequest();
        setUpUpdateProfileRequest();
    }

    @DisplayName("유저 Api Service - updateUserNickname")
    @Test
    void testUpdateUserNickname() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.existsUserByNickname(anyString())).thenReturn(false);
        when(badWordFiltering.filter(eq(NEW_NICKNAME))).thenReturn(false);

        UpdateNicknameResponse response = userApiService.updateUserNickName(updateNicknameRequest, user.getId());

        assertEquals(NEW_NICKNAME, response.getNickname());
    }

    @DisplayName("유저 Api Service - updateUserProfile")
    @Test
    void testUpdateUserProfile() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.existsUserByNickname(anyString())).thenReturn(false);
        when(badWordFiltering.filter(eq(NEW_NICKNAME))).thenReturn(false);
        when(badWordFiltering.filter(eq(NEW_INTRODUCE))).thenReturn(false);

        UpdateProfileResponse response = userApiService.updateUserProfile(updateProfileRequest, user.getId());

        assertEquals(NEW_NICKNAME, response.getNickname());
        assertEquals(NEW_INTRODUCE, response.getIntroduce());
    }

    @DisplayName("유저 Api Service - updateProfileImage")
    @Test
    void testUpdateProfileImage() throws IOException {
        when(profileImageRepository.findByUserId(anyLong())).thenReturn(Optional.of(profileImage));
        when(s3Uploader.upload(any(MultipartFile.class))).thenReturn(STORED_FILE_NAME);
        when(s3Uploader.generatePresignedProfileImageUrl(anyString(), anyInt())).thenReturn(new URL(MULTIPART_FILE_URL));

        ProfileImageResponse response = userApiService.updateProfileImage(multipartFile, user.getId());

        assertEquals(MULTIPART_FILE_URL, response.getProfileImageFileName());
    }

    @DisplayName("유저 Api Service - checkIfNicknameIsAvailable")
    @Test
    void testCheckIfNicknameIsAvailable() {
        assertDoesNotThrow(() -> userApiService.checkIfNicknameIsAvailable(NICKNAME));
    }

}
