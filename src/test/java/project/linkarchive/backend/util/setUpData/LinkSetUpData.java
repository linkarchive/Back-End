package project.linkarchive.backend.util.setUpData;

import org.junit.jupiter.api.BeforeEach;
import project.linkarchive.backend.hashtag.domain.HashTag;
import project.linkarchive.backend.hashtag.response.TagResponse;
import project.linkarchive.backend.link.domain.Link;
import project.linkarchive.backend.link.request.CreateLinkRequest;
import project.linkarchive.backend.link.response.LinkMetaDataResponse;
import project.linkarchive.backend.link.response.linkList.LinkResponse;
import project.linkarchive.backend.link.response.linkList.UserLinkListResponse;
import project.linkarchive.backend.link.response.linkList.UserLinkResponse;
import project.linkarchive.backend.link.response.linkarchive.ArchiveResponse;
import project.linkarchive.backend.link.response.linkarchive.UserArchiveResponse;
import project.linkarchive.backend.link.response.linkarchive.UserLinkArchiveResponse;
import project.linkarchive.backend.user.domain.User;

import static project.linkarchive.backend.util.constant.Constants.*;

public class LinkSetUpData extends SetUpData {

    @BeforeEach
    public void setup() {
        setUpUser();
        setUpLink();
        setUpTagList();
        setUpCreateLinkRequest();
        setupTagResponseList();
        setUpArchiveResponse();
        setupUserArchiveResponseList();
        setUpUserLinkArchiveResponse();
        setUpLinkResponse();
        setUpUserLinkResponseList();
        setUpUserLinkListResponse();
        setUpLinkMetaDataResponse();
    }

    private void setUpUser() {
        user = User.builder()
                .socialId(SOCIAL_ID)
                .nickname(NICKNAME)
                .email(EMAIL)
                .introduce(INTRODUCE)
                .build();
    }

    private void setUpLink() {
        link = Link.builder()
                .url(URL)
                .title(TITLE)
                .description(DESCRIPTION)
                .thumbnail(THUMBNAIL)
                .bookMarkCount(BOOKMARK_COUNT)
                .user(user)
                .build();
    }

    private void setUpTagList() {
        for (int i = 1; i <= 10; i++) {
            hashTag = HashTag.builder()
                    .tag(TAG + i)
                    .build();
            tagList.add(hashTag.getTag());
        }
    }

    private void setUpCreateLinkRequest() {
        createLinkRequest = new CreateLinkRequest(URL, TITLE, DESCRIPTION, THUMBNAIL, tagList);
    }

    private void setupTagResponseList() {
        for (String tag : tagList) {
            tagResponse = new TagResponse(tag);
            tagResponseList.add(tagResponse);
        }
    }

    private void setUpArchiveResponse() {
        archiveResponse = new ArchiveResponse(ID, NICKNAME, PROFILE_IMAGE_FILENAME, ID, URL, TITLE, DESCRIPTION, THUMBNAIL, BOOKMARK_COUNT, CREATED_AT, UPDATED_AT);
    }

    private void setupUserArchiveResponseList() {
        for (int i = 1; i <= 10; i++) {
            userArchiveResponse = new UserArchiveResponse((long) i, NICKNAME, PROFILE_IMAGE_FILENAME, (long) i, URL, TITLE, DESCRIPTION, THUMBNAIL, BOOKMARK_COUNT, IS_READ, IS_MARK, tagResponseList, CREATED_AT, UPDATED_AT);
            userArchiveResponseList.add(userArchiveResponse);
        }
    }

    private void setUpUserLinkArchiveResponse() {
        userLinkArchiveResponse = new UserLinkArchiveResponse(userArchiveResponseList, HAS_NEXT);
    }

    private void setUpLinkResponse() {
        linkResponse = new LinkResponse(ID, URL, TITLE, DESCRIPTION, THUMBNAIL, BOOKMARK_COUNT, CREATED_AT, UPDATED_AT);
    }

    private void setUpUserLinkResponseList() {
        for (int i = 1; i <= 10; i++) {
            getUserLinkResponse = new UserLinkResponse((long) i, URL, TITLE, DESCRIPTION, THUMBNAIL, BOOKMARK_COUNT, IS_READ, IS_MARK, tagResponseList, CREATED_AT, UPDATED_AT);
            userLinkResponseList.add(getUserLinkResponse);
        }
    }

    private void setUpUserLinkListResponse() {
        userLinkListResponse = new UserLinkListResponse(userLinkResponseList, HAS_NEXT);
    }

    private void setUpLinkMetaDataResponse() {
        linkMetaDataResponse = new LinkMetaDataResponse(META_TITLE, META_DESCRIPTION, META_THUMBNAIL);
    }

}