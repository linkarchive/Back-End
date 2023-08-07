package project.linkarchive.backend.util.setUpData;

import org.springframework.web.multipart.MultipartFile;
import project.linkarchive.backend.auth.domain.RefreshToken;
import project.linkarchive.backend.auth.response.KakaoProfile;
import project.linkarchive.backend.bookmark.domain.BookMark;
import project.linkarchive.backend.hashtag.domain.HashTag;
import project.linkarchive.backend.hashtag.domain.UserHashTag;
import project.linkarchive.backend.hashtag.request.CreateTagRequest;
import project.linkarchive.backend.hashtag.response.TagResponse;
import project.linkarchive.backend.isLinkRead.domain.IsLinkRead;
import project.linkarchive.backend.link.domain.Link;
import project.linkarchive.backend.link.domain.LinkHashTag;
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

import java.util.ArrayList;
import java.util.List;

public class MockDataGenerator {

    protected User user;
    protected KakaoProfile kakaoProfile;
    protected ProfileImage profileImage;
    protected AuthInfo authInfo;
    protected MultipartFile multipartFile;
    protected RefreshToken refreshToken;
    protected Link link;
    protected HashTag hashTag;
    protected UserHashTag userHashTag;
    protected LinkHashTag linkHashTag;
    protected BookMark bookMark;
    protected IsLinkRead isLinkRead;

    protected UpdateNicknameRequest updateNicknameRequest;
    protected UpdateProfileRequest updateProfileRequest;
    protected CreateLinkRequest createLinkRequest;
    protected CreateTagRequest createTagRequest;

    protected MyProfileResponse myProfileResponse;
    protected UpdateNicknameResponse updateNicknameResponse;
    protected UpdateProfileResponse updateProfileResponse;
    protected ProfileImageResponse profileImageResponse;
    protected ArchiveResponse archiveResponse;
    protected UserArchiveResponse userArchiveResponse;
    protected UserLinkArchiveResponse userLinkArchiveResponse;
    protected LinkResponse linkResponse;
    protected UserLinkListResponse userLinkListResponse;
    protected UserLinkResponse getUserLinkResponse;
    protected LinkMetaDataResponse linkMetaDataResponse;
    protected TagResponse tagResponse;

    protected List<UserArchiveResponse> userArchiveResponseList = new ArrayList<>();
    protected List<UserLinkResponse> userLinkResponseList = new ArrayList<>();
    protected List<String> tagList = new ArrayList<>();
    protected List<TagResponse> tagResponseList = new ArrayList<>();
    protected List<LinkHashTag> linkHashTagList = new ArrayList<>();

}