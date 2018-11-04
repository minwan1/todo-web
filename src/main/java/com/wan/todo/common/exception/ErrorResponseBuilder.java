package com.wan.todo.common.exception;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class ErrorResponseBuilder {

    static ErrorResponse newTypeValue(Exception e, HttpServletRequest request, ErrorCode errorCode, Object value) {
        final ErrorResponse.Detail detail = ErrorDetailBuilder.newType(e, request, value);
        return buildError(errorCode, detail);
    }

    static ErrorResponse newType(Exception e, HttpServletRequest request, ErrorCode errorCode) {
        final ErrorResponse.Detail detail = ErrorDetailBuilder.newType(e, request);
        return buildError(errorCode, detail);
    }


    static ErrorResponse newTypeFieldError(Exception e, HttpServletRequest request, ErrorCode errorCode, List<ErrorResponse.FieldError> errors, Object value) {
        final ErrorResponse.Detail detail = ErrorDetailBuilder.newType(e, request, value);
        return buildFieldErrors(errorCode, detail, errors);
    }

    private static ErrorResponse buildError(ErrorCode errorCode, ErrorResponse.Detail detail) {
        return ErrorResponse.builder()
                .code(errorCode.code())
                .status(errorCode.status())
                .message(errorCode.message())
                .detail(detail)
                .build();
    }

    private static ErrorResponse buildFieldErrors(ErrorCode errorCode, ErrorResponse.Detail detail, List<ErrorResponse.FieldError> errors) {
        return ErrorResponse.builder()
                .code(errorCode.code())
                .status(errorCode.status())
                .message(errorCode.message())
                .errors(errors)
                .detail(detail)
                .build();
    }

}