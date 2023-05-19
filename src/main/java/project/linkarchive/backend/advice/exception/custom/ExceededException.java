package project.linkarchive.backend.advice.exception.custom;

import lombok.Getter;
import project.linkarchive.backend.advice.exception.BusinessException;
import project.linkarchive.backend.advice.exception.ExceptionCodeConst;

@Getter
public class ExceededException extends BusinessException {

    public ExceededException(ExceptionCodeConst exceptionCodeConst) {
        super(exceptionCodeConst);
    }

}
