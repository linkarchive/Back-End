package project.linkarchive.backend.advice.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends BusinessException {

    public NotFoundException(ExceptionCodeConst exceptionCodeConst) {
        super(exceptionCodeConst);
    }

}