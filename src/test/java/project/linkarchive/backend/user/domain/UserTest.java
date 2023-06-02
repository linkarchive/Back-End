package project.linkarchive.backend.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.auth.response.KakaoProfile;
import project.linkarchive.backend.util.constant.SetUpData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTest extends SetUpData {

    @DisplayName("유저 getSocialId - Domain")
    @Test
    void testGetSocialId() {
        String socialId = user.getSocialId();

        assertNotNull(socialId);
        assertEquals(socialId, user.getSocialId());
    }

    @DisplayName("유저 getEmail - Domain")
    @Test
    void testGetEmail() {
        String email = user.getEmail();

        assertNotNull(email);
        assertEquals(email, user.getEmail());
    }

    @DisplayName("유저 getNickname - Domain")
    @Test
    void testGetNickname() {
        String nickname = user.getNickname();

        assertNotNull(nickname);
        assertEquals(nickname, user.getNickname());
    }

    @DisplayName("유저 getIntroduce - Domain")
    @Test
    void testGetIntroduce() {
        String introduce = user.getIntroduce();

        assertNotNull(introduce);
        assertEquals(introduce, user.getIntroduce());
    }

    @DisplayName("유저 Builder 패턴 - Domain")
    @Test
    void testBuilder() {
        String socialId = user.getSocialId();
        String email = user.getEmail();
        String nickname = user.getNickname();
        String introduce = user.getIntroduce();

        User getUser = User.builder()
                .socialId(socialId)
                .email(email)
                .nickname(nickname)
                .introduce(introduce)
                .build();

        assertNotNull(getUser);
        assertEquals(socialId, getUser.getSocialId());
        assertEquals(email, getUser.getEmail());
        assertEquals(nickname, getUser.getNickname());
        assertEquals(introduce, getUser.getIntroduce());
    }

    @DisplayName("유저 Build method - Domain")
    @Test
    void testBuild() {
        String socialId = user.getSocialId();
        String email = user.getEmail();
        String nickname = "";
        String introduce = "";

        KakaoProfile kakaoProfile = new KakaoProfile(socialId, email);

        User getUser = User.build(kakaoProfile);

        assertNotNull(kakaoProfile);
        assertNotNull(getUser);
        assertEquals(socialId, getUser.getSocialId());
        assertEquals(email, getUser.getEmail());
        assertEquals(nickname, getUser.getNickname());
        assertEquals(introduce, getUser.getIntroduce());
    }

    @DisplayName("유저 updateUserProfile - Domain")
    @Test
    void testUpdateUserProfile() {
        String newNickname = updateProfileRequest.getNickname();
        String newIntroduce = updateProfileRequest.getIntroduce();

        user.updateProfile(updateProfileRequest);

        assertNotNull(newNickname);
        assertNotNull(newIntroduce);
        assertEquals(newNickname, user.getNickname());
        assertEquals(newIntroduce, user.getIntroduce());
    }

    @DisplayName("유저 updateUserNickName - Domain")
    @Test
    void testUpdateUserNickName() {
        String newNickname = updateNicknameRequest.getNickname();

        user.updateNickName(updateNicknameRequest);

        assertNotNull(newNickname);
        assertEquals(newNickname, user.getNickname());
    }

}