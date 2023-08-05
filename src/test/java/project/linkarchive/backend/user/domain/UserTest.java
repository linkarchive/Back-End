package project.linkarchive.backend.user.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static project.linkarchive.backend.util.constant.Constants.*;

class UserTest extends SetUpMockData {

    @BeforeEach
    public void setup() {
        setUpUser();
    }

    @DisplayName("유저 getUserId - Domain")
    @Test
    void testGetUserId() {
        assertEquals(USER_ID, user.getId());
    }

    @DisplayName("유저 getSocialId - Domain")
    @Test
    void testGetSocialId() {
        assertEquals(SOCIAL_ID, user.getSocialId());
    }

    @DisplayName("유저 getEmail - Domain")
    @Test
    void testGetEmail() {
        assertEquals(EMAIL, user.getEmail());
    }

    @DisplayName("유저 getNickname - Domain")
    @Test
    void testGetNickname() {
        assertEquals(EMPTY, user.getNickname());
    }

    @DisplayName("유저 getIntroduce - Domain")
    @Test
    void testGetIntroduce() {
        assertEquals(EMPTY, user.getIntroduce());
    }

    @DisplayName("유저 getFollowerCount - Domain")
    @Test
    void testGetFollowerCount() {
        assertEquals(FOLLOWER_COUNT, user.getFollowerCount());
    }

    @DisplayName("유저 getFollowingCount - Domain")
    @Test
    void testGetFollowingCount() {
        assertEquals(FOLLOWING_COUNT, user.getFollowingCount());
    }

    @DisplayName("유저 getProfileImage - Domain")
    @Test
    void testGetProfileImage() {
        assertEquals(profileImage, user.getProfileImage());
    }

    @DisplayName("유저 생성자 - Domain")
    @Test
    void testUserConstructor() {
        user = new User(USER_ID, SOCIAL_ID, EMAIL, NICKNAME, INTRODUCE, FOLLOWER_COUNT, FOLLOWING_COUNT, profileImage);

        assertEquals(USER_ID, user.getId());
        assertEquals(SOCIAL_ID, user.getSocialId());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(NICKNAME, user.getNickname());
        assertEquals(INTRODUCE, user.getIntroduce());
        assertEquals(FOLLOWER_COUNT, user.getFollowerCount());
        assertEquals(FOLLOWING_COUNT, user.getFollowingCount());
        assertEquals(profileImage, user.getProfileImage());
    }

    @DisplayName("유저 Builder 패턴 - Domain")
    @Test
    void testBuilder() {
        user = User.builder()
                .id(USER_ID)
                .socialId(SOCIAL_ID)
                .email(EMAIL)
                .nickname(NICKNAME)
                .introduce(INTRODUCE)
                .followerCount(FOLLOWER_COUNT)
                .followingCount(FOLLOWING_COUNT)
                .profileImage(profileImage)
                .build();

        assertEquals(USER_ID, user.getId());
        assertEquals(SOCIAL_ID, user.getSocialId());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(NICKNAME, user.getNickname());
        assertEquals(INTRODUCE, user.getIntroduce());
        assertEquals(FOLLOWER_COUNT, user.getFollowerCount());
        assertEquals(FOLLOWING_COUNT, user.getFollowingCount());
        assertEquals(profileImage, user.getProfileImage());
    }

    @DisplayName("유저 create method - Domain")
    @Test
    void testCreate() {
        setUpKaKaoProfile();
        user = User.create(kakaoProfile, profileImage);

        assertEquals(SOCIAL_ID, user.getSocialId());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(EMPTY, user.getNickname());
        assertEquals(EMPTY, user.getIntroduce());
        assertEquals(FOLLOWER_COUNT, user.getFollowerCount());
        assertEquals(FOLLOWING_COUNT, user.getFollowingCount());
        assertEquals(profileImage, user.getProfileImage());
    }

    @DisplayName("유저 updateUserNickName - Domain")
    @Test
    void testUpdateUserNickName() {
        String oldNickname = user.getNickname();

        setUpUpdateNicknameRequest();
        user.updateNickName(updateNicknameRequest);
        String newNickname = user.getNickname();

        assertNotEquals(oldNickname, newNickname);
        assertEquals(NEW_NICKNAME, newNickname);
    }

    @DisplayName("유저 updateUserProfile - Domain")
    @Test
    void testUpdateUserProfile() {
        String oldNickname = user.getNickname();
        String oldIntroduce = user.getIntroduce();

        setUpUpdateProfileRequest();
        user.updateProfile(updateProfileRequest);
        String newNickname = user.getNickname();
        String newIntroduce = user.getIntroduce();

        assertNotEquals(oldNickname, newNickname);
        assertNotEquals(oldIntroduce, newIntroduce);
        assertEquals(NEW_NICKNAME, newNickname);
        assertEquals(NEW_INTRODUCE, newIntroduce);
    }

}