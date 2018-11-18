package com.wan.todo.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ResponseBody
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        final ErrorResponse errorResponse = ErrorResponse.valueOf(ErrorCode.INPUT_VALUE_INVALID, e.getBindingResult());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ErrorCodeException.class)
    protected ResponseEntity<ErrorResponse> handleErrorCodeException(ErrorCodeException e) {
        log.error("ErrorCodeException", e);
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponse errorResponse = ErrorResponse.valueOf(errorCode);
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorCode.status()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception e) {
        log.error("handleUnexpectedException", e);
        final ErrorCode errorCode = ErrorCode.UNEXPECTED_EXCEPTION;
        final ErrorResponse errorResponse = ErrorResponse.valueOf(errorCode);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
