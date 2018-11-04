package com.wan.todo.todo.exception;

import com.wan.todo.common.exception.ErrorCode;
import com.wan.todo.common.exception.ErrorCodeException;

public class CompleteRequirementFailException extends ErrorCodeException {

    public CompleteRequirementFailException() {
        super(ErrorCode.COMPLETE_REQUIREMENT_FAIL);
    }
}
