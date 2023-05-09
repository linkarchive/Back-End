package project.linkarchive.backend.advice.exception;

import lombok.Getter;

@Getter
public enum ExceptionCodeConst {

    NOT_FOUND_USER(404, "NOT_FOUND_USER", "유저가 존재하지 않습니다."),
    NOT_FOUND_URL(404, "NOT_FOUND_URL", "존재하지 않는 Url입니다."),

    ALREADY_EXIST_TAG(409, "ALREADY_EXIST_TAG", "이미 존재하는 태그입니다."),
    ALREADY_EXIST_BOOKMARK(409, "ALREADY_EXIST_BOOKMARK", "이미 마크가 된 Url입니다.");

    private final int status;
    private final String code;
    private final String message;

    ExceptionCodeConst(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}