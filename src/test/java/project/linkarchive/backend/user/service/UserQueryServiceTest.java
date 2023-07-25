package project.linkarchive.backend.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.user.response.ProfileResponse;
import project.linkarchive.backend.util.service.UserSetUpService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static project.linkarchive.backend.util.constant.Constants.*;

class UserQueryServiceTest extends UserSetUpService {

    @BeforeEach
    void setUp() {
        setUpUser();
        setUpMultipartFile();
        setUpProfileImage();
    }

    @DisplayName("유저 Query Service - getMyProfile")
    @Test
    void testGetMyProfile() throws MalformedURLException {
        when(userRepository.findById(USER_ID))
                .thenReturn(Optional.of(user));
        when(s3Uploader.generatePresignedProfileImageUrl(
                anyString(),
                EXPIRATION_TIME_MINUTE)
        ).thenReturn(new URL(PROFILE_IMAGE_URL));

        ProfileResponse response = userQueryService.getMyProfile(user.getId());

        assertEquals(user.getId(), response.getId());
        assertEquals(PROFILE_IMAGE_URL, response.getProfileImageFileName());
    }

}