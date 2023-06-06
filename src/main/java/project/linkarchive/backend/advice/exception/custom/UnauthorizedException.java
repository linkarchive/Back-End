package project.linkarchive.backend.advice.exception.custom;

import project.linkarchive.backend.advice.exception.BusinessException;
import project.linkarchive.backend.advice.exception.ExceptionCodeConst;

public class UnauthorizedException extends BusinessException {

    public UnauthorizedException(ExceptionCodeConst exceptionCodeConst) {
        super(exceptionCodeConst);
    }

}