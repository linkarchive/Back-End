package project.linkarchive.backend.util.setUpData;

import org.springframework.mock.web.MockMultipartFile;
import project.linkarchive.backend.auth.response.KakaoAccount;
import project.linkarchive.backend.auth.response.KakaoProfile;
import project.linkarchive.backend.hashtag.domain.HashTag;
import project.linkarchive.backend.link.domain.Link;
import project.linkarchive.backend.link.request.CreateLinkRequest;
import project.linkarchive.backend.link.response.LinkMetaDataResponse;
import project.linkarchive.backend.link.response.linkList.LinkResponse;
import project.linkarchive.backend.link.response.linkList.UserLinkListResponse;
import project.linkarchive.backend.link.response.linkList.UserLinkResponse;
import project.linkarchive.backend.link.response.linkarchive.ArchiveResponse;
import project.linkarchive.backend.link.response.linkarchive.UserArchiveResponse;
import project.linkarchive.backend.link.response.linkarchive.UserLinkArchiveResponse;
import project.linkarchive.backend.profileImage.domain.ProfileImage;
import project.linkarchive.backend.profileImage.response.ProfileImageResponse;
import project.linkarchive.backend.security.AuthInfo;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.request.UpdateNicknameRequest;
import project.linkarchive.backend.user.request.UpdateProfileRequest;
import project.linkarchive.backend.user.response.MyProfileResponse;
import project.linkarchive.backend.user.response.UpdateNicknameResponse;
import project.linkarchive.backend.user.response.UpdateProfileResponse;

import static project.linkarchive.backend.link.enums.LinkStatus.ACTIVE;
import static project.linkarchive.backend.util.constant.Constants.*;

public class SetUpMockData extends MockDataGenerator {

    protected void setUpUser() {
        user = User.builder()
                .id(USER_ID)
                .socialId(SOCIAL_ID)
                .email(EMAIL)
                .nickname(EMPTY)
                .introduce(EMPTY)
                .followerCount(FOLLOWER_COUNT)
                .followingCount(FOLLOWING_COUNT)
                .profileImage(profileImage)
                .build();
    }

    protected void setUpProfileImage() {
        profileImage = ProfileImage.builder()
                .id(PROFILE_IMAGE_ID)
                .profileImageFilename(PROFILE_IMAGE_FILENAME)
                .build();
    }

    protected void setUpKaKaoProfile() {
        kakaoProfile = new KakaoProfile(SOCIAL_ID, new KakaoAccount(EMAIL));
    }

    protected void setUpOauthInfo() {
        authInfo = new AuthInfo(USER_ID);
    }

    protected void setUpMultipartFile() {
        multipartFile = new MockMultipartFile(PROFILE_IMAGE_URL, MULTIPART_FILE_NAME, CONTENT_TYPE, MULTIPART_FILE_DATA);
    }

    protected void setUpLink() {
        link = Link.builder()
                .id(LINK_ID)
                .url(URL)
                .title(TITLE)
                .description(DESCRIPTION)
                .thumbnail(THUMBNAIL)
                .bookMarkCount(BOOKMARK_COUNT)
                .linkStatus(ACTIVE)
                .user(user)
                .build();
    }

    protected void setUpUpdateNicknameRequest() {
        updateNicknameRequest = new UpdateNicknameRequest(NEW_NICKNAME);
    }

    protected void setUpUpdateProfileRequest() {
        updateProfileRequest = new UpdateProfileRequest(NEW_NICKNAME, NEW_INTRODUCE);
    }

    protected void setUpTagList() {
        for (int i = 1; i <= 10; i++) {
            hashTag = HashTag.builder()
                    .tag(TAG + i)
                    .build();
            tagList.add(hashTag.getTag());
        }
    }

    protected void setUpCreateLinkRequest() {
        createLinkRequest = new CreateLinkRequest(URL, TITLE, DESCRIPTION, THUMBNAIL, tagList);
    }

    protected void setUpProfileResponse() {
        myProfileResponse = new MyProfileResponse(user, PRE_SIGNED_URL);
    }

    protected void setUpUpdateNicknameResponse() {
        updateNicknameResponse = new UpdateNicknameResponse(NEW_NICKNAME);
    }

    protected void setUpUpdateProfileResponse() {
        updateProfileResponse = new UpdateProfileResponse(user);
    }

    protected void setUpProfileImageResponse() {
        profileImageResponse = new ProfileImageResponse(PROFILE_IMAGE_FILENAME);
    }

    protected void setUpArchiveResponse() {
        archiveResponse = new ArchiveResponse(USER_ID, NICKNAME, PROFILE_IMAGE_FILENAME, LINK_ID, URL, TITLE, DESCRIPTION, THUMBNAIL, BOOKMARK_COUNT, CREATED_AT, UPDATED_AT);
    }

    protected void setupUserArchiveResponseList() {
        for (int i = 1; i <= 10; i++) {
            userArchiveResponse = new UserArchiveResponse((long) i, NICKNAME, PROFILE_IMAGE_FILENAME, (long) i, URL, TITLE, DESCRIPTION, THUMBNAIL, BOOKMARK_COUNT, IS_READ, IS_MARK, tagResponseList, CREATED_AT, UPDATED_AT);
            userArchiveResponseList.add(userArchiveResponse);
        }
    }

    protected void setUpUserLinkArchiveResponse() {
        userLinkArchiveResponse = new UserLinkArchiveResponse(userArchiveResponseList, HAS_NEXT);
    }

    protected void setUpLinkResponse() {
        linkResponse = new LinkResponse(ID, URL, TITLE, DESCRIPTION, THUMBNAIL, BOOKMARK_COUNT, CREATED_AT, UPDATED_AT);
    }

    protected void setUpUserLinkResponseList() {
        for (int i = 1; i <= 10; i++) {
            getUserLinkResponse = new UserLinkResponse((long) i, URL, TITLE, DESCRIPTION, THUMBNAIL, BOOKMARK_COUNT, IS_READ, IS_MARK, tagResponseList, CREATED_AT, UPDATED_AT);
            userLinkResponseList.add(getUserLinkResponse);
        }
    }

    protected void setUpUserLinkListResponse() {
        userLinkListResponse = new UserLinkListResponse(userLinkResponseList, HAS_NEXT);
    }

    protected void setUpLinkMetaDataResponse() {
        linkMetaDataResponse = new LinkMetaDataResponse(META_TITLE, META_DESCRIPTION, META_THUMBNAIL);
    }

}