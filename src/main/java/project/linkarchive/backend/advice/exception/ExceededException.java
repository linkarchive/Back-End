package project.linkarchive.backend.advice.exception;

import lombok.Getter;

@Getter
public class ExceededException extends BusinessException {

    public ExceededException(ExceptionCodeConst exceptionCodeConst) {
        super(exceptionCodeConst);
    }

}
