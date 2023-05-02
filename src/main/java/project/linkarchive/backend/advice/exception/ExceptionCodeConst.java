package project.linkarchive.backend.advice.exception;

import lombok.Getter;

@Getter
public enum ExceptionCodeConst {

    //SAMPLE CONST
    NOT_FOUND_USER("NOT_FOUND_USER", "유저가 존재하지 않습니다.");

    private final String code;
    private final String message;

    ExceptionCodeConst(String code, String message) {
        this.code = code;
        this.message = message;
    }

}