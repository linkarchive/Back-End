package project.linkarchive.backend.advice.exception;

import lombok.Getter;

@Getter
public enum ExceptionCodeConst {

    INVALID_TOKEN(400, "BAD_REQUEST", "잘못된 토큰입니다."),
    INVALID_AUTHORIZATION_CODE(400, "INVALID_AUTHORIZATION_CODE", "잘못된 인가코드입니다."),
    EXCEEDED_TAG_10_LIMIT(400, "EXCEEDED_TAG_LIMIT", "태그의 개수가 10개를 초과할 수 없습니다."),

    NOT_FOUND_USER(404, "NOT_FOUND_USER", "유저가 존재하지 않습니다."),
    NOT_FOUND_LINK(404, "NOT_FOUND_URL", "존재하지 않는 Link 입니다."),
    NOT_FOUND_BOOKMARK(404, "NOT_FOUND_BOOKMARK", "존재하지 않는 북마크입니다."),

    ALREADY_EXIST_TAG(409, "ALREADY_EXIST_TAG", "이미 존재하는 태그입니다."),
    ALREADY_EXIST_BOOKMARK(409, "ALREADY_EXIST_BOOKMARK", "이미 마크가 된 Link 입니다.");

    private final int status;
    private final String code;
    private final String message;

    ExceptionCodeConst(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}