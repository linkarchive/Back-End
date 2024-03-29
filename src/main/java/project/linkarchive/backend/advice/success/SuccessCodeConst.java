package project.linkarchive.backend.advice.success;

import lombok.Getter;

@Getter
public enum SuccessCodeConst {

    LOGIN_SUCCESS(200, "LOGIN", "회원가입에 성공했습니다."),
    UPDATE_USER_PROFILE(200, "UPDATE_USER_PROFILE", "유저의 프로필이 수정되었습니다."),
    UPDATE_PROFILE_IMAGE(200, "UPDATE_PROFILE_IMAGE", "프로필 이미지가 수정되었습니다."),
    UPDATE_NICKNAME(200, "UPDATE_NICKNAME", "닉네임이 수정되었습니다."),
    BOOK_MARK_CANCEL(200, "BOOK_MARK_CANCEL", "북마크가 취소되었습니다."),
    AVAILABLE_NICKNAME(200, "AVAILABLE_NICKNAME", "사용가능한 닉네임입니다"),
    UNFOLLOW_USER(200, "UNFOLLOW_USER", "팔로우가 취소되었습니다."),
    LINK_RESTORE(200, "LINK_RESTORE", "링크가 복구되었습니다"),

    LINK_CREATE(201, "LINK_CREATE", "Link 가 생성되었습니다."),
    USER_TAG_CREATE(201, "USER_TAG_CREATE", "유저가 자주 사용하는 태그가 생성되었습니다."),
    BOOK_MARK(201, "BOOK_MARK", "북마크 되었습니다."),
    LINK_READ(201, "LINK_READ", "링크를 읽었어요"),
    FOLLOW_USER(201, "FOLLOW_USER", "팔로우 되었습니다."),

    LINK_DELETE(202, "LINK_DELETE", "링크가 삭제되었습니다.");

    private final int status;
    private final String code;
    private final String message;

    SuccessCodeConst(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}