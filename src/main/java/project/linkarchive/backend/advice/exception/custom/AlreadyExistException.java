package project.linkarchive.backend.advice.exception.custom;

import project.linkarchive.backend.advice.exception.BusinessException;
import project.linkarchive.backend.advice.exception.ExceptionCodeConst;

public class AlreadyExistException extends BusinessException {

    public AlreadyExistException(ExceptionCodeConst exceptionCodeConst) {
        super(exceptionCodeConst);
    }

}