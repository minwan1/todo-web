package com.wan.todo.common.exception;

import java.util.List;


public class ErrorResponseBuilder {

    static ErrorResponse newType(ErrorCode errorCode) {
        return buildError(errorCode);
    }

    static ErrorResponse newTypeFieldError(ErrorCode errorCode, List<ErrorResponse.FieldError> errors) {
        return buildFieldErrors(errorCode, errors);
    }

    private static ErrorResponse buildError(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .code(errorCode.code())
                .status(errorCode.status())
                .message(errorCode.message())
                .build();
    }

    private static ErrorResponse buildFieldErrors(ErrorCode errorCode, List<ErrorResponse.FieldError> errors) {
        return ErrorResponse.builder()
                .code(errorCode.code())
                .status(errorCode.status())
                .message(errorCode.message())
                .errors(errors)
                .build();
    }

}