package project.linkarchive.backend.advice.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExceptionResponse {

    private String code;
    private String message;

    public ExceptionResponse(ExceptionCodeConst exceptionCodeConst) {
        this.code = exceptionCodeConst.getCode();
        this.message = exceptionCodeConst.getMessage();
    }

}