package project.linkarchive.backend.advice.success;

import lombok.Getter;

@Getter
public enum SuccessCodeConst {

    // SAMPLE
    SINGUP_SUCCESS(200, "SIGNUP", "회원가입에 성공했습니다.");

    private int status;
    private String code;
    private String message;

    SuccessCodeConst(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}