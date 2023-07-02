package project.linkarchive.backend.util.setUpData;

import project.linkarchive.backend.auth.domain.RefreshToken;
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
import project.linkarchive.backend.user.domain.User;
import project.linkarchive.backend.user.request.UpdateNicknameRequest;
import project.linkarchive.backend.user.request.UpdateProfileRequest;
import project.linkarchive.backend.user.response.ProfileResponse;
import project.linkarchive.backend.user.response.UpdateNicknameResponse;
import project.linkarchive.backend.user.response.UpdateProfileResponse;

import java.util.ArrayList;
import java.util.List;

public class SetUpData {

    public User user;
    public ProfileImage profileImage;
    public RefreshToken refreshToken;
    public Link link;
    public HashTag hashTag;
    public UserHashTag userHashTag;
    public LinkHashTag linkHashTag;
    public BookMark bookMark;
    public IsLinkRead isLinkRead;

    public UpdateNicknameRequest updateNicknameRequest;
    public UpdateProfileRequest updateProfileRequest;
    public CreateLinkRequest createLinkRequest;
    public CreateTagRequest createTagRequest;

    public ProfileResponse profileResponse;
    public UpdateNicknameResponse updateNicknameResponse;
    public UpdateProfileResponse updateProfileResponse;
    public ArchiveResponse archiveResponse;
    public UserArchiveResponse userArchiveResponse;
    public UserLinkArchiveResponse userLinkArchiveResponse;
    public LinkResponse linkResponse;
    public UserLinkListResponse userLinkListResponse;
    public UserLinkResponse getUserLinkResponse;
    public LinkMetaDataResponse linkMetaDataResponse;
    public TagResponse tagResponse;

    public List<UserArchiveResponse> userArchiveResponseList = new ArrayList<>();
    public List<UserLinkResponse> userLinkResponseList = new ArrayList<>();
    public List<String> tagList = new ArrayList<>();
    public List<TagResponse> tagResponseList = new ArrayList<>();
    public List<LinkHashTag> linkHashTagList = new ArrayList<>();

}