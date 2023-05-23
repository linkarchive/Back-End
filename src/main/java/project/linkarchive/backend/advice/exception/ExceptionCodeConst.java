package project.linkarchive.backend.advice.exception;

import lombok.Getter;

@Getter
public enum ExceptionCodeConst {

    INVALID_TOKEN(400, "BAD_REQUEST", "잘못된 토큰입니다."),
    INVALID_AUTHORIZATION_CODE(400, "INVALID_AUTHORIZATION_CODE", "잘못된 인가코드입니다."),

    NOT_FOUND_USER(404, "NOT_FOUND_USER", "유저가 존재하지 않습니다."),
    NOT_FOUND_PROFILE_IMAGE(404, "NOT_FOUND_PROFILE_IMAGE", "프로필 이미지가 존재하지 않습니다."),
    NOT_FOUND_LINK(404, "NOT_FOUND_URL", "존재하지 않는 Link 입니다."),
    NOT_FOUND_BOOKMARK(404, "NOT_FOUND_BOOKMARK", "존재하지 않는 북마크입니다."),

    EMPTY_UPLOAD_FILE(406, "EMPTY_UPLOAD_FILE", "파일을 찾을 수 없습니다."),
    NOT_ACCEPTABLE_CONTENT_TYPE(406, "NOT_ACCEPTABLE_CONTENT_TYPE", "jpeg, jpg, png 파일만 업로드 할 수 있습니다."),

    ALREADY_EXIST_TAG(409, "ALREADY_EXIST_TAG", "이미 존재하는 태그입니다."),
    ALREADY_EXIST_BOOKMARK(409, "ALREADY_EXIST_BOOKMARK", "이미 마크가 된 Link 입니다."),
    ALREADY_EXIST_NICKNAME(409, "ALREADY_EXIST_NICKNAME", "이미 존재하는 닉네임 입니다."),

    LENGTH_REQUIRED_TAG(416, "LENGTH_REQUIRED_TAG", "Tag의 길이를 2글자 이상 8글자 이하로 작성해주세요."),
    LENGTH_REQUIRED_NICKNAME(416, "LENGTH_REQUIRED_TAG", "닉네임의 길이를 2글자 이상 8글자 이하로 작성해주세요."),

    EXCEEDED_TAG_LIMIT_10(416, "EXCEEDED_TAG_LIMIT", "태그의 개수가 10개를 초과할 수 없습니다."),
    EXCEEDED_TAG_SIZE(416, "EXCEEDED_TAG_SIZE", "해시태그 조회 갯수를 초과했습니다.");

    private final int status;
    private final String code;
    private final String message;

    ExceptionCodeConst(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}