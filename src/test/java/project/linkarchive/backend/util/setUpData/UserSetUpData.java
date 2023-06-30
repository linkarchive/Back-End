package project.linkarchive.backend.util.setUpData;

import org.junit.jupiter.api.BeforeEach;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.request.UpdateNicknameRequest;
import project.linkarchive.backend.user.request.UpdateProfileRequest;

import static project.linkarchive.backend.util.constant.Constants.*;

public class UserSetUpData extends SetUpData {

    @BeforeEach
    public void setup() {

        user = User.builder()
                .socialId(SOCIAL_ID)
                .nickname(NICKNAME)
                .email(EMAIL)
                .introduce(INTRODUCE)
                .build();

        updateNicknameRequest = new UpdateNicknameRequest(NEW_NICKNAME);
        updateProfileRequest = new UpdateProfileRequest(NEW_NICKNAME, NEW_INTRODUCE);

    }

}