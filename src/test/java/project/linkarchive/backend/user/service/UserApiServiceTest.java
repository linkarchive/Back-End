package project.linkarchive.backend.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.profileImage.response.ProfileImageResponse;
import project.linkarchive.backend.user.response.UpdateNicknameResponse;
import project.linkarchive.backend.user.response.UpdateProfileResponse;
import project.linkarchive.backend.util.service.UserSetUpService;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static project.linkarchive.backend.util.constant.Constants.*;

class UserApiServiceTest extends UserSetUpService {

    @BeforeEach
    public void setUp() {
        setUpUser();
    }

    @DisplayName("유저 Api Service - updateUserNickname")
    @Test
    void testUpdateUserNickname() {
        setUpUpdateNicknameRequest();
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(userRepository.existsUserByNickname(NEW_NICKNAME)).thenReturn(false);
        when(badWordFiltering.filter(eq(NEW_NICKNAME))).thenReturn(false);

        UpdateNicknameResponse response = userApiService.updateUserNickName(updateNicknameRequest, user.getId());

        assertEquals(NEW_NICKNAME, response.getNickname());

        verify(userRepository).findById(USER_ID);
        verify(userRepository).existsUserByNickname(NEW_NICKNAME);
        verify(badWordFiltering).filter(NEW_NICKNAME);
    }

    @DisplayName("유저 Api Service - updateUserProfile")
    @Test
    void testUpdateUserProfile() {
        setUpUpdateProfileRequest();
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(userRepository.existsUserByNickname(NEW_NICKNAME)).thenReturn(false);
        when(badWordFiltering.filter(eq(NEW_NICKNAME))).thenReturn(false);
        when(badWordFiltering.filter(eq(NEW_INTRODUCE))).thenReturn(false);

        UpdateProfileResponse response = userApiService.updateUserProfile(updateProfileRequest, user.getId());

        assertEquals(NEW_NICKNAME, response.getNickname());
        assertEquals(NEW_INTRODUCE, response.getIntroduce());

        verify(userRepository).findById(USER_ID);
        verify(userRepository).existsUserByNickname(NEW_NICKNAME);
        verify(badWordFiltering).filter(NEW_NICKNAME);
        verify(badWordFiltering).filter(NEW_INTRODUCE);
    }

    @DisplayName("유저 Api Service - updateProfileImage")
    @Test
    void testUpdateProfileImage() throws IOException {
        setUpProfileImage();
        setUpMultipartFile();
        when(profileImageRepository.findByUserId(USER_ID)).thenReturn(Optional.of(profileImage));
        when(s3Uploader.upload(multipartFile)).thenReturn(STORED_FILE_NAME);
        when(s3Uploader.generatePresignedProfileImageUrl(STORED_FILE_NAME, EXPIRATION_TIME_MINUTE)).thenReturn(new URL(MULTIPART_FILE_URL));

        ProfileImageResponse response = userApiService.updateProfileImage(multipartFile, user.getId());

        assertEquals(MULTIPART_FILE_URL, response.getProfileImageFileName());

        verify(profileImageRepository).findByUserId(USER_ID);
        verify(s3Uploader).upload(multipartFile);
        verify(s3Uploader).generatePresignedProfileImageUrl(STORED_FILE_NAME, EXPIRATION_TIME_MINUTE);
    }

    @DisplayName("유저 Api Service - checkIfNicknameIsAvailable")
    @Test
    void testCheckIfNicknameIsAvailable() {
        assertDoesNotThrow(() -> userApiService.checkIfNicknameIsAvailable(NEW_NICKNAME));
    }

}
