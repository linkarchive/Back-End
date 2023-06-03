package project.linkarchive.backend.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.auth.response.KakaoProfile;
import project.linkarchive.backend.util.constant.SetUpData;

import static org.junit.jupiter.api.Assertions.*;
import static project.linkarchive.backend.util.constant.Constants.*;

class UserTest extends SetUpData {

    @DisplayName("유저 getSocialId - Domain")
    @Test
    void testGetSocialId() {
        String socialId = user.getSocialId();

        assertNotNull(socialId);
        assertEquals(SOCIAL_ID, socialId);
    }

    @DisplayName("유저 getEmail - Domain")
    @Test
    void testGetEmail() {
        String email = user.getEmail();

        assertNotNull(email);
        assertEquals(EMAIL, email);
    }

    @DisplayName("유저 getNickname - Domain")
    @Test
    void testGetNickname() {
        String nickname = user.getNickname();

        assertNotNull(nickname);
        assertEquals(NICKNAME, nickname);
    }

    @DisplayName("유저 getIntroduce - Domain")
    @Test
    void testGetIntroduce() {
        String introduce = user.getIntroduce();

        assertNotNull(introduce);
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

        assertNotNull(getUser);
        assertEquals(SOCIAL_ID, getUser.getSocialId());
        assertEquals(EMAIL, getUser.getEmail());
        assertEquals(NICKNAME, getUser.getNickname());
        assertEquals(INTRODUCE, getUser.getIntroduce());
    }

    @DisplayName("유저 Build method - Domain")
    @Test
    void testBuild() {
        KakaoProfile kakaoProfile = new KakaoProfile(SOCIAL_ID, EMAIL);

        User getUser = User.build(kakaoProfile);

        assertNotNull(kakaoProfile);
        assertNotNull(getUser);
        assertEquals(SOCIAL_ID, getUser.getSocialId());
        assertEquals(EMAIL, getUser.getEmail());
        assertEquals(EMPTY, getUser.getNickname());
        assertEquals(EMPTY, getUser.getIntroduce());
        assertNotEquals(NICKNAME, getUser.getNickname());
        assertNotEquals(INTRODUCE, getUser.getIntroduce());
    }

    @DisplayName("유저 updateUserProfile - Domain")
    @Test
    void testUpdateUserProfile() {
        user.updateProfile(updateProfileRequest);

        String updatedNickname = user.getNickname();
        String updatedIntroduce = user.getIntroduce();

        assertNotNull(updatedNickname);
        assertNotNull(updatedIntroduce);
        assertEquals(NEW_NICKNAME, updatedNickname);
        assertEquals(NEW_INTRODUCE, updatedIntroduce);
    }

    @DisplayName("유저 updateUserNickName - Domain")
    @Test
    void testUpdateUserNickName() {
        user.updateNickName(updateNicknameRequest);

        String updatedNickname = user.getNickname();


        assertNotNull(updatedNickname);
        assertEquals(NEW_NICKNAME, updatedNickname);
    }

}