package project.linkarchive.backend.advice.exception.custom;

import lombok.Getter;
import project.linkarchive.backend.advice.exception.BusinessException;
import project.linkarchive.backend.advice.exception.ExceptionCodeConst;

@Getter
public class NotFoundException extends BusinessException {

    public NotFoundException(ExceptionCodeConst exceptionCodeConst) {
        super(exceptionCodeConst);
    }

}