package project.linkarchive.backend.advice.exception.custom;

import project.linkarchive.backend.advice.exception.BusinessException;
import project.linkarchive.backend.advice.exception.ExceptionCodeConst;

public class ExceededException extends BusinessException {

    public ExceededException(ExceptionCodeConst exceptionCodeConst) {
        super(exceptionCodeConst);
    }

}
