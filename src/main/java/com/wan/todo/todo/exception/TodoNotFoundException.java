package com.wan.todo.todo.exception;

import com.wan.todo.common.exception.ErrorCode;
import com.wan.todo.common.exception.ErrorCodeException;

public class TodoNotFoundException extends ErrorCodeException {
    public TodoNotFoundException(Object value) {
        super(ErrorCode.TODO_NOT_FOUND, value);
    }
}
