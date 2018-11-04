package com.wan.todo.common.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
public abstract class FieldErrorException extends RuntimeException {

    private ErrorCode errorCode;
    private List<ErrorResponse.FieldError> errors = new ArrayList<>();

    public FieldErrorException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    protected void addFieldError(ErrorResponse.FieldError fieldError) {
        errors.add(fieldError);
    }

}
