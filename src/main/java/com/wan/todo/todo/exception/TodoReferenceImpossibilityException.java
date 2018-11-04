package com.wan.todo.todo.exception;

import com.wan.todo.common.exception.ErrorCode;
import com.wan.todo.common.exception.ErrorCodeException;

public class TodoReferenceImpossibilityException extends ErrorCodeException {
    public TodoReferenceImpossibilityException() {
        super(ErrorCode.TODO_REFERENCE_IMPOSSIBILITY);
    }
}
