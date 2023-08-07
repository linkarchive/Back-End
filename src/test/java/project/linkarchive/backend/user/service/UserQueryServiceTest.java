package project.linkarchive.backend.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.linkarchive.backend.advice.exception.custom.NotFoundException;
import project.linkarchive.backend.relationship.repository.RelationshipRepository;
import project.linkarchive.backend.s3.S3Uploader;
import project.linkarchive.backend.user.repository.UserRepository;
import project.linkarchive.backend.user.response.MyProfileResponse;
import project.linkarchive.backend.user.response.UserProfileResponse;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static project.linkarchive.backend.util.constant.Constants.*;

@ExtendWith(MockitoExtension.class)
class UserQueryServiceTest extends SetUpMockData {

    @Mock
    protected UserRepository userRepository;

    @Mock
    protected RelationshipRepository relationshipRepository;

    @Mock
    protected S3Uploader s3Uploader;

    @InjectMocks
    protected UserQueryService userQueryService;

    @BeforeEach
    void setUp() {
        setUpProfileImage();
        setUpUser();
        setUpOauthInfo();
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

        MyProfileResponse response = userQueryService.getMyProfile(user.getId());

        validateResponse(response);
    }

    @DisplayName("유저 Query Service - getMyProfile - User Not Found")
    @Test
    void testGetMyProfile_UserNotFound() {
        when(userRepository.findById(USER_ID))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class, () -> userQueryService.getMyProfile(user.getId())
        );
    }

    @DisplayName("유저 Query Service - getUserProfile")
    @Test
    void testGetUserProfile() throws MalformedURLException {
        when(userRepository.findById(USER_ID))
                .thenReturn(Optional.of(user));
        when(s3Uploader.generatePresignedProfileImageUrl(
                anyString(),
                eq(EXPIRATION_TIME_MINUTE))
        ).thenReturn(new URL(PROFILE_IMAGE_URL));
        when(relationshipRepository.existsByFolloweeIdAndFollowerId(user.getId(), authInfo.getId()))
                .thenReturn(true);

        UserProfileResponse response = userQueryService.getUserProfile(user.getId(), authInfo);

        validateResponse(response);
    }

    @DisplayName("유저 Query Service - getUserProfile - User Not Found")
    @Test
    void testGetUserProfile_UserNotFound() {
        when(userRepository.findById(USER_ID))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class, () -> userQueryService.getUserProfile(user.getId(), authInfo)
        );
    }

    private void validateResponse(MyProfileResponse response) {
        assertEquals(user.getId(), response.getId());
        assertEquals(user.getNickname(), response.getNickname());
        assertEquals(PROFILE_IMAGE_URL, response.getProfileImageFileName());
    }

    private void validateResponse(UserProfileResponse response) {
        assertEquals(user.getId(), response.getId());
        assertEquals(user.getNickname(), response.getNickname());
        assertEquals(user.getIntroduce(), response.getIntroduce());
        assertEquals(PROFILE_IMAGE_URL, response.getProfileImageFileName());
        assertEquals(user.getFollowerCount(), response.getFollowerCount());
        assertEquals(user.getFollowingCount(), response.getFollowingCount());
    }

}