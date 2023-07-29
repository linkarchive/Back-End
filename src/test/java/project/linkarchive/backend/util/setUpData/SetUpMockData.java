package project.linkarchive.backend.util.setUpData;

import org.springframework.mock.web.MockMultipartFile;
import project.linkarchive.backend.auth.response.KakaoAccount;
import project.linkarchive.backend.auth.response.KakaoProfile;
import project.linkarchive.backend.profileImage.domain.ProfileImage;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.request.UpdateNicknameRequest;
import project.linkarchive.backend.user.request.UpdateProfileRequest;
import project.linkarchive.backend.user.response.ProfileResponse;
import project.linkarchive.backend.user.response.UpdateNicknameResponse;
import project.linkarchive.backend.user.response.UpdateProfileResponse;

import static project.linkarchive.backend.util.constant.Constants.*;

public class SetUpMockData extends MockDataGenerator {

    protected void setUpUser() {
        user = User.builder()
                .id(USER_ID)
                .socialId(SOCIAL_ID)
                .nickname(EMPTY)
                .email(EMAIL)
                .introduce(EMPTY)
                .profileImage(profileImage)
                .build();
    }

    protected void setUpKaKaoProfile() {
        kakaoProfile = new KakaoProfile(SOCIAL_ID, new KakaoAccount(EMAIL));
    }

    protected void setUpProfileImage() {
        profileImage = ProfileImage.builder()
                .id(PROFILE_IMAGE_ID)
                .profileImageFilename(PROFILE_IMAGE_FILENAME)
                .build();
    }

    protected void setUpMultipartFile() {
        multipartFile = new MockMultipartFile(PROFILE_IMAGE_URL, MULTIPART_FILE_NAME, CONTENT_TYPE, MULTIPART_FILE_DATA);
    }

    protected void setUpUpdateNicknameRequest() {
        updateNicknameRequest = new UpdateNicknameRequest(NEW_NICKNAME);
    }

    protected void setUpUpdateProfileRequest() {
        updateProfileRequest = new UpdateProfileRequest(NEW_NICKNAME, NEW_INTRODUCE);
    }

    protected void setUpProfileResponse() {
        profileResponse = new ProfileResponse(user, PRE_SIGNED_URL);
    }

    protected void setUpUpdateNicknameResponse() {
        updateNicknameResponse = new UpdateNicknameResponse(NEW_NICKNAME);
    }

    protected void setUpUpdateProfileResponse() {
        updateProfileResponse = new UpdateProfileResponse(user);
    }

}