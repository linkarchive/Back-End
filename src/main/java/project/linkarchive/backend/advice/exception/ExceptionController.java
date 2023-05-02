package project.linkarchive.backend.advice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundUserException(BusinessException businessException) {
        return ResponseEntity.ok(new ExceptionResponse(businessException.getExceptionCodeConst()));
    }

}