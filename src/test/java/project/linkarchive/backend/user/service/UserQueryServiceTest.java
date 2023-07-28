package project.linkarchive.backend.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.user.response.ProfileResponse;
import project.linkarchive.backend.util.service.UserSetUpService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static project.linkarchive.backend.util.constant.Constants.*;

class UserQueryServiceTest extends UserSetUpService {

    @BeforeEach
    void setUp() {
        setUpProfileImage();
        setUpUser();
    }

    @DisplayName("유저 Query Service - getMyProfile")
    @Test
    void testGetMyProfile() throws MalformedURLException {
        when(userRepository.findById(USER_ID))
                .thenReturn(Optional.of(user));
        when(s3Uploader.generatePresignedProfileImageUrl(
                anyString(),
                eq(EXPIRATION_TIME_MINUTE))
        ).thenReturn(new URL(PROFILE_IMAGE_URL));

        ProfileResponse response = userQueryService.getMyProfile(user.getId());

        validateResponse(response);
    }

    @DisplayName("유저 Query Service - getMyProfile - User Not Found")
    @Test
    void testGetMyProfile_UserNotFound() {
        when(userRepository.findById(USER_ID))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userQueryService.getMyProfile(user.getId()));
    }

    @DisplayName("유저 Query Service - getUserProfile")
    @Test
    void testGetUserProfile() throws MalformedURLException {
        when(userRepository.findByNickname(EMPTY))
                .thenReturn(Optional.of(user));
        when(s3Uploader.generatePresignedProfileImageUrl(
                anyString(),
                eq(EXPIRATION_TIME_MINUTE))
        ).thenReturn(new URL(PROFILE_IMAGE_URL));

        ProfileResponse response = userQueryService.getUserProfile(user.getNickname());

        validateResponse(response);
    }

    @DisplayName("유저 Query Service - getUserProfile - User Not Found")
    @Test
    void testGetUserProfile_UserNotFound() {
        when(userRepository.findByNickname(EMPTY))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userQueryService.getUserProfile(user.getNickname()));
    }

    private void validateResponse(ProfileResponse response) {
        assertEquals(user.getId(), response.getId());
        assertEquals(user.getNickname(), response.getNickname());
        assertEquals(PROFILE_IMAGE_URL, response.getProfileImageFileName());
    }

}