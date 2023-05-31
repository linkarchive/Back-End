package project.linkarchive.backend.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.linkarchive.backend.auth.response.KakaoProfile;
import project.linkarchive.backend.user.request.NickNameRequest;
import project.linkarchive.backend.user.request.UpdateProfileRequest;
import project.linkarchive.backend.util.constant.SetUpData;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest extends SetUpData {

    @DisplayName("유저 build method - Domain")
    @Test
    public void testUserConstructor() {
        String socialId = user.getSocialId();
        String email = user.getEmail();
        String nickname = "";
        String introduce = "";

        KakaoProfile kakaoProfile = new KakaoProfile(socialId, email);

        User getUser = User.build(kakaoProfile);

        assertEquals(socialId, getUser.getSocialId());
        assertEquals(email, getUser.getEmail());
        assertEquals(nickname, getUser.getNickname());
        assertEquals(introduce, getUser.getIntroduce());
    }

    @DisplayName("유저 updateUserProfile - Domain")
    @Test
    public void testUpdateUserProfile() {
        String newNickname = "New Test Nickname";
        String newIntroduce = "New Test Introduce";

        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest(newNickname, newIntroduce);
        user.updateUserProfile(updateProfileRequest);

        assertEquals(newNickname, user.getNickname());
        assertEquals(newIntroduce, user.getIntroduce());
    }

    @DisplayName("유저 updateUserNickName - Domain")
    @Test
    public void testUpdateUserNickName() {
        String newNickname = "New Test Nickname";

        NickNameRequest nickNameRequest = new NickNameRequest(newNickname);
        user.updateUserNickName(nickNameRequest);

        assertEquals(newNickname, user.getNickname());
    }

}