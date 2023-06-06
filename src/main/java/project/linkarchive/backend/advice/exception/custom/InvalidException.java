package project.linkarchive.backend.advice.exception.custom;

import project.linkarchive.backend.advice.exception.BusinessException;
import project.linkarchive.backend.advice.exception.ExceptionCodeConst;

public class InvalidException extends BusinessException {

    public InvalidException(ExceptionCodeConst exceptionCodeConst) {
        super(exceptionCodeConst);
    }

}