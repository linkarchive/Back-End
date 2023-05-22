package project.linkarchive.backend.advice.exception.custom;

import project.linkarchive.backend.advice.exception.BusinessException;
import project.linkarchive.backend.advice.exception.ExceptionCodeConst;

public class LengthRequiredException extends BusinessException {

    public LengthRequiredException(ExceptionCodeConst exceptionCodeConst) {
        super(exceptionCodeConst);
    }

}