package com.wan.todo.common.exception;

import lombok.Getter;

@Getter
public class ErrorCodeException extends RuntimeException {

    private ErrorCode errorCode;
    private Object value;

    public ErrorCodeException(ErrorCode errorCode) {
        super(errorCode.message());
        this.errorCode = errorCode;
    }

    public ErrorCodeException(ErrorCode errorCode, Object value) {
        super(errorCode.message());
        this.value = value;
        this.errorCode = errorCode;
    }
}
