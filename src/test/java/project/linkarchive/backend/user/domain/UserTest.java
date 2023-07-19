package project.linkarchive.backend.user.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.util.setUpData.UserSetUpData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static project.linkarchive.backend.util.constant.Constants.*;

class UserTest extends UserSetUpData {

    @BeforeEach
    public void setup() {
        setUpUser();
        setUpUpdateNicknameRequest();
        setUpUpdateProfileRequest();
        setUpKakaoProfile();
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

    @DisplayName("유저 생성자 - Domain")
    @Test
    void testUserConstructor() {
        user = new User(USER_ID, SOCIAL_ID, EMAIL, EMPTY, EMPTY);

        assertEquals(USER_ID, user.getId());
        assertEquals(SOCIAL_ID, user.getSocialId());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(EMPTY, user.getNickname());
        assertEquals(EMPTY, user.getIntroduce());
    }

    @DisplayName("유저 Builder 패턴 - Domain")
    @Test
    void testBuilder() {
        user = User.builder()
                .id(USER_ID)
                .socialId(SOCIAL_ID)
                .email(EMAIL)
                .nickname(EMPTY)
                .introduce(EMPTY)
                .build();

        assertEquals(USER_ID, user.getId());
        assertEquals(SOCIAL_ID, user.getSocialId());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(EMPTY, user.getNickname());
        assertEquals(EMPTY, user.getIntroduce());
    }

    @DisplayName("유저 create method - Domain")
    @Test
    void testCreate() {
        user = User.create(kakaoProfile);

        assertEquals(SOCIAL_ID, user.getSocialId());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(EMPTY, user.getNickname());
        assertEquals(EMPTY, user.getIntroduce());
    }

    @DisplayName("유저 updateUserNickName - Domain")
    @Test
    void testUpdateUserNickName() {
        String oldNickname = user.getNickname();

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

        user.updateProfile(updateProfileRequest);
        String newNickname = user.getNickname();
        String newIntroduce = user.getIntroduce();

        assertNotEquals(oldNickname, newNickname);
        assertNotEquals(oldIntroduce, newIntroduce);
        assertEquals(NEW_NICKNAME, newNickname);
        assertEquals(NEW_INTRODUCE, newIntroduce);
    }

}