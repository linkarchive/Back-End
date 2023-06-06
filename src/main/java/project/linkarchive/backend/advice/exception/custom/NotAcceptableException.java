package project.linkarchive.backend.advice.exception.custom;

import project.linkarchive.backend.advice.exception.BusinessException;
import project.linkarchive.backend.advice.exception.ExceptionCodeConst;

public class NotAcceptableException extends BusinessException {

    public NotAcceptableException(ExceptionCodeConst exceptionCodeConst) {
        super(exceptionCodeConst);
    }

}