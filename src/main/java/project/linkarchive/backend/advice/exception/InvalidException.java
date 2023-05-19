package project.linkarchive.backend.advice.exception;

import lombok.Getter;

@Getter
public class InvalidException extends BusinessException {

    public InvalidException(ExceptionCodeConst exceptionCodeConst) {
        super(exceptionCodeConst);
    }

}