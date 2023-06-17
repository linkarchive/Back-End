package project.linkarchive.backend.advice.exception.custom;

import project.linkarchive.backend.advice.exception.BusinessException;
import project.linkarchive.backend.advice.exception.ExceptionCodeConst;

public class ForbiddenException extends BusinessException {

    public ForbiddenException(ExceptionCodeConst exceptionCodeConst) {
        super(exceptionCodeConst);
    }

}