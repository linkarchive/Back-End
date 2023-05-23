package project.linkarchive.backend.advice.success;

import lombok.Getter;

@Getter
public enum SuccessCodeConst {

    LOGIN_SUCCESS(200, "LOGIN", "회원가입에 성공했습니다."),
    UPDATE_PROFILE_IMAGE(200, "UPDATE_PROFILE_IMAGE", "프로필 이미지가 수정되었습니다."),
    UPDATE_NICKNAME(200, "UPDATE_NICKNAME", "닉네임이 수정되었습니다."),
    BOOK_MARK_CANCEL(200, "BOOK_MARK_CANCEL", "북마크가 취소되었습니다."),

    LINK_CREATE(201, "LINK_CREATE", "Link 가 생성되었습니다."),
    USER_TAG_CREATE(201, "USER_TAG_CREATE", "유저가 자주 사용하는 태그가 생성되었습니다."),
    BOOK_MARK(201, "BOOK_MARK", "북마크 되었습니다.");

    private int status;
    private String code;
    private String message;

    SuccessCodeConst(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}