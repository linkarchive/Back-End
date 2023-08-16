package project.linkarchive.backend.user.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.SetUpMockData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static project.linkarchive.backend.auth.AuthProvider.TEST;
import static project.linkarchive.backend.notification.enums.NotificationPreference.ALLOWED;
import static project.linkarchive.backend.util.constant.Constants.*;

class UserTest extends SetUpMockData {

    @BeforeEach
    public void setup() {
        setUpUser();
    }

    @DisplayName("User Getter - Domain")
    @Test
    void testGetter() {
        assertEquals(USER_ID, user.getId());
        assertEquals(SOCIAL_ID, user.getSocialId());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(EMPTY, user.getNickname());
        assertEquals(EMPTY, user.getIntroduce());
        assertEquals(FOLLOWER_COUNT, user.getFollowerCount());
        assertEquals(FOLLOWING_COUNT, user.getFollowingCount());
        assertEquals(profileImage, user.getProfileImage());
    }

    @DisplayName("User Constructor - Domain")
    @Test
    void testConstructor() {
        user = new User(USER_ID, SOCIAL_ID, TEST, EMAIL, NICKNAME, INTRODUCE, FOLLOWER_COUNT, FOLLOWING_COUNT, USER_LINK_COUNT, USER_BOOKMARK_COUNT, ALLOWED, profileImage, pin);

        assertEquals(USER_ID, user.getId());
        assertEquals(SOCIAL_ID, user.getSocialId());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(NICKNAME, user.getNickname());
        assertEquals(INTRODUCE, user.getIntroduce());
        assertEquals(FOLLOWER_COUNT, user.getFollowerCount());
        assertEquals(FOLLOWING_COUNT, user.getFollowingCount());
        assertEquals(profileImage, user.getProfileImage());
    }

    @DisplayName("User Builder Pattern - Domain")
    @Test
    void testBuilder() {
        user = User.builder()
                .id(USER_ID)
                .authProvider(TEST)
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

    @DisplayName("User Create Method - Domain")
    @Test
    void testCreate() {
        setUpKaKaoProfile();

        user = User.create(TEST, kakaoProfile, profileImage, pin);

        assertEquals(SOCIAL_ID, user.getSocialId());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(EMPTY, user.getNickname());
        assertEquals(EMPTY, user.getIntroduce());
        assertEquals(FOLLOWER_COUNT, user.getFollowerCount());
        assertEquals(FOLLOWING_COUNT, user.getFollowingCount());
        assertEquals(profileImage, user.getProfileImage());
    }

    @DisplayName("User UpdateNickName - Domain")
    @Test
    void testUpdateNickName() {
        String oldNickname = user.getNickname();

        setUpUpdateNicknameRequest();
        user.updateNickName(updateNicknameRequest);
        String newNickname = user.getNickname();

        assertNotEquals(oldNickname, newNickname);
        assertEquals(NEW_NICKNAME, newNickname);
    }

    @DisplayName("User UpdateProfile - Domain")
    @Test
    void testUpdateProfile() {
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