package project.linkarchive.backend.util.constant;

import project.linkarchive.backend.link.enums.LinkStatus;

import java.time.LocalDateTime;

import static project.linkarchive.backend.link.enums.LinkStatus.ACTIVE;

public class Constants {

    public static final String EMPTY = "";
    public static final Long EMPTY_LONG_VAL = 0L;
    public static final Long MODIFY_LONG_VAL = 1L;
    public static final Long ID = 1L;

    public static final String SOCIAL_ID = "Test Social Id";
    public static final String EMAIL = "Test Email";
    public static final String NICKNAME = "Test Nickname";
    public static final String INTRODUCE = "Test Introduce";

    public static final String PROFILE_IMAGE_FILENAME = "Test ProfileImage FileName";

    public static final String REFRESH_TOKEN = "Test RefreshToken";
    public static final String AGENT = "Test Agent";

    public static final String URL = "Test Url";
    public static final String TITLE = "Test Title";
    public static final String DESCRIPTION = "Test Description";
    public static final String THUMBNAIL = "Test Thumbnail";
    public static final Long BOOKMARK_COUNT = 10L;
    public static final LinkStatus LINK_STATUS = ACTIVE;

    public static final String META_TITLE = "Test Meta Title";
    public static final String META_DESCRIPTION = "Test Meta Description";
    public static final String META_THUMBNAIL = "Test Meta Thumbnail";

    public static final String PRE_SIGNED_URL = "Test Pre_Signed Url";

    public static final String TAG = "Test Tag";

    public static final Long USAGE_COUNT = 10L;
    public static final Boolean IS_MARK = true;
    public static final Boolean IS_READ = true;
    public static final Boolean HAS_NEXT = true;

    public static final String NEW_NICKNAME = "New Test Nickname";
    public static final String NEW_INTRODUCE = "New Test Introduce";

    public static final String NEW_PROFILE_IMAGE_FILENAME = "New Test ProfileImageFilename";

    public static final LocalDateTime CREATED_AT = LocalDateTime.of(1995, 4, 19, 12, 34, 56);
    public static final LocalDateTime UPDATED_AT = LocalDateTime.of(2023, 4, 19, 12, 34, 56);

}