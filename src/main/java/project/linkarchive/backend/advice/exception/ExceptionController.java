package project.linkarchive.backend.advice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundUserException(InvalidException businessException) {
        return ResponseEntity.status(BAD_REQUEST).body(new ExceptionResponse(businessException.getExceptionCodeConst()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundUserException(NotFoundException businessException) {
        return ResponseEntity.status(NOT_FOUND).body(new ExceptionResponse(businessException.getExceptionCodeConst()));
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundUserException(AlreadyExistException businessException) {
        return ResponseEntity.status(CONFLICT).body(new ExceptionResponse(businessException.getExceptionCodeConst()));
    }

    @ExceptionHandler(ExceededException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundUserException(ExceededException businessException) {
        return ResponseEntity.status(REQUESTED_RANGE_NOT_SATISFIABLE).body(new ExceptionResponse(businessException.getExceptionCodeConst()));
    }

}