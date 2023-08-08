package project.linkarchive.backend.util.constant;

import project.linkarchive.backend.link.enums.LinkStatus;

import java.time.LocalDateTime;

import static project.linkarchive.backend.link.enums.LinkStatus.ACTIVE;
import static project.linkarchive.backend.link.enums.LinkStatus.TRASH;

public class Constants {

    public static final String EMPTY = "";
    public static final Long ID = 1L;

    public static final Long USER_ID = 1L;
    public static final String SOCIAL_ID = "Test_Social_Id";
    public static final String EMAIL = "Test_Email";
    public static final String NICKNAME = "TestNickname";
    public static final String INTRODUCE = "Test_Introduce";
    public static final int FOLLOWER_COUNT = 0;
    public static final int FOLLOWING_COUNT = 0;

    public static final String NONE_SOCIAL_ID = "Test_None_Social_Id";
    public static final String NONE_NICKNAME = "Test_None_Nickname";

    public static final Long PROFILE_IMAGE_ID = 1L;
    public static final String PROFILE_IMAGE_FILENAME = "Test_ProfileImage_FileName";

    public static final String STORED_FILE_NAME = "Test_Stored_FileName";
    public static final String PROFILE_IMAGE_URL = "http://Test_New_ProfileImage_FileName";
    public static final String MULTIPART_FILE_NAME = "TEST_New_File_Image.jpg";
    public static final String CONTENT_TYPE = "image/jpg";
    public static final byte[] MULTIPART_FILE_DATA = "image data".getBytes();
    public static final int EXPIRATION_TIME_MINUTE = 3600000;

    public static final Long REFRESH_TOKEN_ID = 1L;
    public static final String REFRESH_TOKEN = "Test_RefreshToken";
    public static final String AGENT = "Test_Agent";

    public static final Long LINK_ID = 1L;
    public static final String URL = "Test_https://url.com";
    public static final String TITLE = "Test_Title";
    public static final String DESCRIPTION = "Test_Description";
    public static final String THUMBNAIL = "Test_Thumbnail";
    public static final Long BOOKMARK_COUNT = 0L;
    public static final LinkStatus LINK_STATUS_ACTIVE = ACTIVE;
    public static final LinkStatus LINK_STATUS_TRASH = TRASH;

    public static final String META_TITLE = "Test_Meta_Title";
    public static final String META_DESCRIPTION = "Test_Meta_Description";
    public static final String META_THUMBNAIL = "Test_Meta_Thumbnail";

    public static final String PRE_SIGNED_URL = "Test_Pre_Signed_Url";

    public static final String TAG = "Test_Tag";

    public static final Long USAGE_COUNT = 10L;
    public static final Boolean IS_MARK = true;
    public static final Boolean IS_READ = true;
    public static final Boolean HAS_NEXT = true;

    public static final String NEW_NICKNAME = "NewTestNickname";
    public static final String NEW_INTRODUCE = "New_Test_Introduce";

    public static final String NEW_PROFILE_IMAGE_FILENAME = "New_Test_ProfileImageFilename";

    public static final LocalDateTime CREATED_AT = LocalDateTime.of(1995, 4, 19, 12, 34, 56);
    public static final LocalDateTime UPDATED_AT = LocalDateTime.of(2023, 4, 19, 12, 34, 56);

}