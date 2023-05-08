package project.linkarchive.backend.advice.success;

import lombok.Getter;

@Getter
public enum SuccessCodeConst {

    LOGIN_SUCCESS(201, "LOGIN", "회원가입에 성공했습니다."),
    URL_CREATE(201, "URL_CREATE", "url 이 생성되었습니다."),
    USER_TAG_CREATE(201, "USER_TAG_CREATE", "유저가 자주 사용하는 태그가 생성되었습니다.");

    private int status;
    private String code;
    private String message;

    SuccessCodeConst(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}