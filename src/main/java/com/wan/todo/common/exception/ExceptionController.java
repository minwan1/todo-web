package com.wan.todo.common.exception;

import com.wan.todo.common.ObjectMapperUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ResponseBody
@ControllerAdvice
@AllArgsConstructor
public class ExceptionController {

    private ObjectMapperUtil mapperUtil;


    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error("handleMethodArgumentNotValidException", e);
        final Object value = e.getBindingResult().getTarget();
        final BindingResult bindingResult = e.getBindingResult();
        final List<FieldError> errors = bindingResult.getFieldErrors();
        final ErrorCode errorCode = ErrorCode.INPUT_VALUE_INVALID;
        final List<ErrorResponse.FieldError> fieldErrors = builderFieldError(errors);
        final ErrorResponse errorResponse = ErrorResponseBuilder.newTypeFieldError(e, request, errorCode, fieldErrors, value);
        log.error(mapperUtil.writeValueAsString(errorResponse));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {
            ErrorCodeException.class,
    })
    protected ResponseEntity<ErrorResponse> handleErrorCodeException(ErrorCodeException e, HttpServletRequest request) {
        log.error("ErrorCodeException", e);
        final HttpStatus status = HttpStatus.valueOf(e.getErrorCode().status());
        final ErrorResponse errorResponse = buildErrorResponse(e, request);
        log.error(mapperUtil.writeValueAsString(errorResponse));
        return new ResponseEntity<>(errorResponse, status);
    }


    @SuppressWarnings("unchecked")
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(final RuntimeException e, HttpServletRequest request) {
        log.error("handleUnexpectedException", e);
        final ErrorCode errorCode = ErrorCode.UNEXPECTED_EXCEPTION;
        final ErrorResponse errorResponse = ErrorResponseBuilder.newType(e, request, errorCode);
        log.error(mapperUtil.writeValueAsString(errorResponse));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private List<ErrorResponse.FieldError> builderFieldError(List<FieldError> errors) {
        return errors.stream()
                .map(error -> ErrorResponse.FieldError.builder()
                        .reason(error.getDefaultMessage())
                        .field(error.getField())
                        .value((String) error.getRejectedValue())
                        .build())
                .collect(Collectors.toList());
    }

    private ErrorResponse buildErrorResponse(ErrorCodeException e, HttpServletRequest request) {
        final ErrorResponse errorResponse;
        if (e.getValue() == null) {
            errorResponse = ErrorResponseBuilder.newType(e, request, e.getErrorCode());
        } else {
            errorResponse = ErrorResponseBuilder.newTypeValue(e, request, e.getErrorCode(), e.getValue());
        }
        return errorResponse;
    }

}
