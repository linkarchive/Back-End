package project.linkarchive.backend.util.setUpData;

import org.springframework.web.multipart.MultipartFile;
import project.linkarchive.backend.auth.domain.RefreshToken;
import project.linkarchive.backend.auth.response.KakaoProfile;
import project.linkarchive.backend.bookmark.domain.Bookmark;
import project.linkarchive.backend.hashtag.domain.Hashtag;
import project.linkarchive.backend.hashtag.domain.UserHashtag;
import project.linkarchive.backend.hashtag.request.CreateTagRequest;
import project.linkarchive.backend.hashtag.response.TagResponse;
import project.linkarchive.backend.isLinkRead.domain.IsLinkRead;
import project.linkarchive.backend.link.domain.Link;
import project.linkarchive.backend.link.domain.LinkHashtag;
import project.linkarchive.backend.link.request.CreateLinkRequest;
import project.linkarchive.backend.link.response.LinkMetaDataResponse;
import project.linkarchive.backend.link.response.linkList.LinkResponse;
import project.linkarchive.backend.link.response.linkList.UserLinkListResponse;
import project.linkarchive.backend.link.response.linkList.UserLinkResponse;
import project.linkarchive.backend.link.response.linkarchive.ArchiveResponse;
import project.linkarchive.backend.link.response.linkarchive.UserArchiveResponse;
import project.linkarchive.backend.link.response.linkarchive.UserLinkArchiveResponse;
import project.linkarchive.backend.link.response.trash.TrashLinkListResponse;
import project.linkarchive.backend.link.response.trash.TrashLinkResponse;
import project.linkarchive.backend.link.response.trash.UserTrashLinkListResponse;
import project.linkarchive.backend.pin.domain.Pin;
import project.linkarchive.backend.profileImage.domain.ProfileImage;
import project.linkarchive.backend.profileImage.response.ProfileImageResponse;
import project.linkarchive.backend.security.AuthInfo;
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.request.UpdateNicknameRequest;
import project.linkarchive.backend.user.request.UpdateProfileRequest;
import project.linkarchive.backend.user.response.MyProfileResponse;
import project.linkarchive.backend.user.response.UpdateNicknameResponse;
import project.linkarchive.backend.user.response.UpdateProfileResponse;
import project.linkarchive.backend.user.response.UserProfileResponse;

import java.util.ArrayList;
import java.util.List;

public class MockDataGenerator {

    protected User user;
    protected KakaoProfile kakaoProfile;
    protected ProfileImage profileImage;
    protected Pin pin;
    protected AuthInfo authInfo;
    protected MultipartFile multipartFile;
    protected RefreshToken refreshToken;
    protected Link link;
    protected Hashtag hashTag;
    protected UserHashtag userHashTag;
    protected LinkHashtag linkHashTag;
    protected Bookmark bookMark;
    protected IsLinkRead isLinkRead;

    protected UpdateNicknameRequest updateNicknameRequest;
    protected UpdateProfileRequest updateProfileRequest;
    protected CreateLinkRequest createLinkRequest;
    protected CreateTagRequest createTagRequest;

    protected MyProfileResponse myProfileResponse;
    protected UserProfileResponse userProfileResponse;
    protected UpdateNicknameResponse updateNicknameResponse;
    protected UpdateProfileResponse updateProfileResponse;
    protected ProfileImageResponse profileImageResponse;
    protected LinkMetaDataResponse linkMetaDataResponse;
    protected ArchiveResponse archiveResponse;
    protected UserArchiveResponse userArchiveResponse;
    protected UserLinkArchiveResponse userLinkArchiveResponse;
    protected LinkResponse linkResponse;
    protected UserLinkListResponse userLinkListResponse;
    protected UserLinkResponse userLinkResponse;
    protected TrashLinkListResponse trashLinkListResponse;
    protected TrashLinkResponse trashLinkResponse;
    protected UserTrashLinkListResponse userTrashLinkListResponse;
    protected TagResponse tagResponse;

    protected List<UserArchiveResponse> userArchiveResponseList = new ArrayList<>();
    protected List<UserLinkResponse> userLinkResponseList = new ArrayList<>();
    protected List<TrashLinkListResponse> trashLinkListResponseList = new ArrayList<>();
    protected List<String> tagList = new ArrayList<>();
    protected List<TagResponse> tagResponseList = new ArrayList<>();
    protected List<LinkHashtag> linkHashtagList = new ArrayList<>();

}