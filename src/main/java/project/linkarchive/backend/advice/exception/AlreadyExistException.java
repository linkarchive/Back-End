package project.linkarchive.backend.advice.exception;

import lombok.Getter;

@Getter
public class AlreadyExistException extends BusinessException {

    public AlreadyExistException(ExceptionCodeConst exceptionCodeConst) {
        super(exceptionCodeConst);
    }

}