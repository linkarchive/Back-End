package project.linkarchive.backend.util.setUpData;

import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.request.UpdateNicknameRequest;
import project.linkarchive.backend.user.request.UpdateProfileRequest;
import project.linkarchive.backend.user.response.ProfileResponse;
import project.linkarchive.backend.user.response.UpdateNicknameResponse;
import project.linkarchive.backend.user.response.UpdateProfileResponse;

import static project.linkarchive.backend.util.constant.Constants.*;

public class UserSetUpData extends SetUpData {

    public void setUpUser() {
        user = User.builder()
                .socialId(SOCIAL_ID)
                .nickname(EMPTY)
                .email(EMAIL)
                .introduce(EMPTY)
                .build();
    }

    public void setUpUpdateNicknameRequest() {
        updateNicknameRequest = new UpdateNicknameRequest(NEW_NICKNAME);
    }

    public void setUpUpdateProfileRequest() {
        updateProfileRequest = new UpdateProfileRequest(NEW_NICKNAME, NEW_INTRODUCE);
    }

    public void setUpProfileResponse() {
        profileResponse = new ProfileResponse(user, PRE_SIGNED_URL);
    }

    public void setUpUpdateNicknameResponse() {
        updateNicknameResponse = new UpdateNicknameResponse(NEW_NICKNAME);
    }

    public void setUpUpdateProfileResponse() {
        updateProfileResponse = new UpdateProfileResponse(user);
    }

}