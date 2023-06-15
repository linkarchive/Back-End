package project.linkarchive.backend.advice.data;

public class Constants {

    public static final String EMPTY = "";
    public static final String BLANK = " ";
    public static final String IMAGE_CONTENT_TYPE = "^image/(jpeg|jpg|png)$";
    public static final String BEARER = "bearer";

    public static final int TOKEN_TYPE_INDEX = 0;
    public static final int TOKEN_DATA_INDEX = 1;
    public static final int MINIMUM_TITLE_LENGTH = 1;
    public static final int MINIMUM_NICKNAME_LENGTH = 2;
    public static final int TOKEN_LENGTH = 2;
    public static final int MINIMUM_TAG_LENGTH = 2;
    public static final int S3_KEY = 3;
    public static final int MAXIMUM_TAG_LENGTH = 8;
    public static final int MAX_TAG_COUNT = 10;
    public static final int MAXIMUM_NICKNAME_LENGTH = 16;
    public static final int MAXIMUM_INTRODUCE_LENGTH = 20;
    public static final int MAX_SIZE = 30;
    public static final int IMAGE_EXPIRATION_TIME = 1000 * 60 * 60;

    public static final Long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 2L;
    public static final Long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 30L;


}