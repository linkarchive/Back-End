package project.linkarchive.backend.advice.data;

import java.util.regex.Pattern;

public class DataConstants {

    public static final String EMPTY = "";
    public static final String BLANK = " ";
    public static final String IMAGE_CONTENT_TYPE = "^image/(jpeg|jpg|png)$";
    public static final String BEARER = "bearer";
    public static final String AUTH_KAKAO = "auth/kakao";
    public static final String LOCAL_EMAIL = "local@gmail.com";
    public static final String SOCIAL_LOGIN = "social_login";
    public static final Pattern PATTERN_REGAX = Pattern.compile("^[\\uAC00-\\uD7A3a-zA-Z0-9]+$");

    public static final int DEFAULT_COUNT = 0;
    public static final int BOOKMARK_DEFAULT_COUNT = 0;
    public static final int HASHTAG_DEFAULT_COUNT = 0;
    public static final int TOKEN_TYPE_INDEX = 0;
    public static final int TOKEN_DATA_INDEX = 1;
    public static final int MINIMUM_TITLE_LENGTH = 1;
    public static final int MINIMUM_NICKNAME_LENGTH = 2;
    public static final int TOKEN_LENGTH = 2;
    public static final int MINIMUM_TAG_LENGTH = 2;
    public static final int S3_KEY = 3;
    public static final int MAXIMUM_TAG_LENGTH = 8;
    public static final int TAG_SIZE = 10;
    public static final int MAXIMUM_NICKNAME_LENGTH = 16;
    public static final int MAXIMUM_INTRODUCE_LENGTH = 20;
    public static final int IMAGE_EXPIRATION_TIME = 1000 * 60 * 60;

    public static final Long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 2L;
    public static final Long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 30L;

}
