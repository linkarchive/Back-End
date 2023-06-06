package project.linkarchive.backend.advice.exception.custom;

import project.linkarchive.backend.advice.exception.BusinessException;
import project.linkarchive.backend.advice.exception.ExceptionCodeConst;

public class NotFoundException extends BusinessException {

    public NotFoundException(ExceptionCodeConst exceptionCodeConst) {
        super(exceptionCodeConst);
    }

}