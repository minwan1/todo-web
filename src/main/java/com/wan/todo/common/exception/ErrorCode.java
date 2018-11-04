package com.wan.todo.common.exception;

public enum ErrorCode {

    //tododomain
    TODO_NOT_FOUND(400, "T001", "TODO_NOT_FOUND"),
    TODO_REFERENCE_IMPOSSIBILITY(400, "T002", "TODO_REFERENCE_IMPOSSIBILITY"),
    COMPLETE_REQUIREMENT_FAIL(400, "C001", "COMPLETE_REQUIREMENT_FAIL"),

    //ETC
    INPUT_VALUE_INVALID(400, "I001", "INPUT_VALUE_INVALID"),

    //500
    UNEXPECTED_EXCEPTION(500, "xxx", "internal server error"),;

    private final String code;
    private final String message;
    private int status;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String message() {
        return this.message;
    }

    public String code() {
        return code;
    }

    public int status() {
        return status;
    }

}
