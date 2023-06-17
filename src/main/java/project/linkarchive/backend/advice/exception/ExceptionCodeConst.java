package project.linkarchive.backend.advice.exception;

import lombok.Getter;

@Getter
public enum ExceptionCodeConst {

    BAD_REQUEST_TOKEN(400, "BAD_REQUEST_TOKEN", "잘못된 토큰 요청입니다."),
    ACCESS_TOKEN_STILL_VALID(400, "ACCESS_TOKEN_STILL_VALID", "엑세스 토큰의 만료시간이 남아있습니다."),

    INVALID_TOKEN(401, "INVALID_TOKEN", "유효하지 않은 토큰입니다. 로그인이 필요합니다."),
    INVALID_AUTHORIZATION_CODE(401, "INVALID_AUTHORIZATION_CODE", "잘못된 인가코드입니다."),
    INVALID_BAD_WORD(400, "INVALID_BAD_WORD", "욕설은 허용하지 않습니다."),

    FORBIDDEN_ACCESS(403, "FORBIDDEN_ACCESS", "권한이 없습니다."),

    NOT_FOUND_USER(404, "NOT_FOUND_USER", "유저가 존재하지 않습니다."),
    NOT_FOUND_TOKEN(404, "NOT_FOUND_TOKEN", "토큰이 존재하지 않습니다."),
    NOT_FOUND_PROFILE_IMAGE(404, "NOT_FOUND_PROFILE_IMAGE", "프로필 이미지가 존재하지 않습니다."),
    NOT_FOUND_LINK(404, "NOT_FOUND_URL", "존재하지 않는 Link 입니다."),
    NOT_FOUND_BOOKMARK(404, "NOT_FOUND_BOOKMARK", "존재하지 않는 북마크입니다."),
    NOT_FOUND_HASHTAG(404, "NOT_FOUND_HASHTAG", "존재하지 않는 태그입니다."),

    EMPTY_UPLOAD_FILE(406, "EMPTY_UPLOAD_FILE", "파일을 찾을 수 없습니다."),
    NOT_ACCEPTABLE_CONTENT_TYPE(406, "NOT_ACCEPTABLE_CONTENT_TYPE", "jpeg, jpg, png 파일만 업로드 할 수 있습니다."),
    INVALID_NICKNAME(406, "INVALID_NICKNAME", "유효하지 않는 닉네임입니다."),
    CONVERSION_FAILED(406, "CONVERSION_FAILED", "MultipartFile -> File 변환 실패"),

    ALREADY_EXIST_TAG(409, "ALREADY_EXIST_TAG", "이미 존재하는 태그입니다."),
    ALREADY_EXIST_BOOKMARK(409, "ALREADY_EXIST_BOOKMARK", "이미 마크가 된 Link 입니다."),
    ALREADY_EXIST_NICKNAME(409, "ALREADY_EXIST_NICKNAME", "이미 존재하는 닉네임 입니다."),
    ALREADY_EXIST_IS_LINK_READ(409, "ALREADY_EXIST_IS_LINK_READ", "이미 읽었던 Link 입니다."),

    LENGTH_REQUIRED_TAG(416, "LENGTH_REQUIRED_TAG", "Tag의 길이를 2글자 이상 8글자 이하로 작성해주세요."),
    LENGTH_REQUIRED_NICKNAME(416, "LENGTH_REQUIRED_NICKNAME", "닉네임의 길이를 2글자 이상 16글자 이하로 작성해주세요."),
    LENGTH_REQUIRED_INTRODUCE(416, "LENGTH_REQUIRED_INTRODUCE", "자기소개는 20글자 이하로 작성해주세요."),
    LENGTH_REQUIRED_TITLE(416, "LENGTH_REQUIRED_TITLE", "링크의 제목을 입력해주세요."),

    EXCEEDED_TAG_LIMIT_10(416, "EXCEEDED_TAG_LIMIT", "태그의 개수가 10개를 초과할 수 없습니다."),
    EXCEEDED_TAG_SIZE(416, "EXCEEDED_TAG_SIZE", "해시태그 조회 갯수를 초과했습니다."),

    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR", "서버 내부 오류가 발생했습니다.");

    private final int status;
    private final String code;
    private final String message;

    ExceptionCodeConst(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}