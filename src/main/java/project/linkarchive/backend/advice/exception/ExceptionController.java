package project.linkarchive.backend.advice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.linkarchive.backend.advice.exception.custom.*;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidException(InvalidException businessException) {
        return ResponseEntity.status(BAD_REQUEST).body(new ExceptionResponse(businessException.getExceptionCodeConst()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorizedException(UnauthorizedException businessException) {
        return ResponseEntity.status(UNAUTHORIZED).body(new ExceptionResponse(businessException.getExceptionCodeConst()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ExceptionResponse> handleForbiddenException(ForbiddenException businessException) {
        return ResponseEntity.status(FORBIDDEN).body(new ExceptionResponse(businessException.getExceptionCodeConst()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundUserException(NotFoundException businessException) {
        return ResponseEntity.status(NOT_FOUND).body(new ExceptionResponse(businessException.getExceptionCodeConst()));
    }

    @ExceptionHandler(NotAcceptableException.class)
    public ResponseEntity<ExceptionResponse> notAcceptableException(NotAcceptableException businessException) {
        return ResponseEntity.status(NOT_ACCEPTABLE).body(new ExceptionResponse(businessException.getExceptionCodeConst()));
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundUserException(AlreadyExistException businessException) {
        return ResponseEntity.status(CONFLICT).body(new ExceptionResponse(businessException.getExceptionCodeConst()));
    }

    @ExceptionHandler(LengthRequiredException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundUserException(LengthRequiredException businessException) {
        return ResponseEntity.status(LENGTH_REQUIRED).body(new ExceptionResponse(businessException.getExceptionCodeConst()));
    }

    @ExceptionHandler(ExceededException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundUserException(ExceededException businessException) {
        return ResponseEntity.status(REQUESTED_RANGE_NOT_SATISFIABLE).body(new ExceptionResponse(businessException.getExceptionCodeConst()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ExceptionCodeConst.INTERNAL_SERVER_ERROR));
    }

}