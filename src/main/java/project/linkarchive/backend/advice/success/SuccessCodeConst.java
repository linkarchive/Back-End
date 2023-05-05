package project.linkarchive.backend.advice.success;

import lombok.Getter;

@Getter
public enum SuccessCodeConst {

    URL_CREATE(201, "URL_CREATE", "url 이 생성되었습니다.");

    private int status;
    private String code;
    private String message;

    SuccessCodeConst(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}