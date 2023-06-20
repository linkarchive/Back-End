package project.linkarchive.backend.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.auth.response.KakaoAccount;
import project.linkarchive.backend.auth.response.KakaoProfile;
import project.linkarchive.backend.util.setUpData.UserSetUpData;

import static org.junit.jupiter.api.Assertions.*;
import static project.linkarchive.backend.util.constant.Constants.*;

class UserTest extends UserSetUpData {

    @DisplayName("유저 getSocialId - Domain")
    @Test
    void testGetSocialId() {
        String socialId = user.getSocialId();

        assertEquals(SOCIAL_ID, socialId);
    }

    @DisplayName("유저 getEmail - Domain")
    @Test
    void testGetEmail() {
        String email = user.getEmail();

        assertEquals(EMAIL, email);
    }

    @DisplayName("유저 getNickname - Domain")
    @Test
    void testGetNickname() {
        String nickname = user.getNickname();

        assertEquals(NICKNAME, nickname);
    }

    @DisplayName("유저 getIntroduce - Domain")
    @Test
    void testGetIntroduce() {
        String introduce = user.getIntroduce();

        assertEquals(INTRODUCE, introduce);
    }

    @DisplayName("유저 Builder 패턴 - Domain")
    @Test
    void testBuilder() {
        User getUser = User.builder()
                .socialId(SOCIAL_ID)
                .email(EMAIL)
                .nickname(NICKNAME)
                .introduce(INTRODUCE)
                .build();

        assertEquals(SOCIAL_ID, getUser.getSocialId());
        assertEquals(EMAIL, getUser.getEmail());
        assertEquals(NICKNAME, getUser.getNickname());
        assertEquals(INTRODUCE, getUser.getIntroduce());
    }

    @DisplayName("유저 create method - Domain")
    @Test
    void testCreate() {
        KakaoProfile kakaoProfile = new KakaoProfile(SOCIAL_ID, new KakaoAccount(EMAIL));
        User getUser = User.create(kakaoProfile);

        assertEquals(SOCIAL_ID, getUser.getSocialId());
        assertEquals(EMAIL, getUser.getEmail());
        assertEquals(EMPTY, getUser.getNickname());
        assertEquals(EMPTY, getUser.getIntroduce());
    }

    @DisplayName("유저 updateUserProfile - Domain")
    @Test
    void testUpdateUserProfile() {
        String oldNickname = user.getNickname();
        String oldIntroduce = user.getIntroduce();

        user.updateProfile(updateProfileRequest);

        assertNotEquals(oldNickname, user.getNickname());
        assertNotEquals(oldIntroduce, user.getIntroduce());
        assertEquals(NEW_NICKNAME, user.getNickname());
        assertEquals(NEW_INTRODUCE, user.getIntroduce());
    }

    @DisplayName("유저 updateUserNickName - Domain")
    @Test
    void testUpdateUserNickName() {
        String oldNickname = user.getNickname();

        user.updateNickName(updateNicknameRequest);


        assertNotEquals(oldNickname, user.getNickname());
        assertEquals(NEW_NICKNAME, user.getNickname());
    }

}