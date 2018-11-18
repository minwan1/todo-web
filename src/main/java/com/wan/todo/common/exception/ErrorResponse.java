package com.wan.todo.common.exception;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    private String message;
    private String code;
    private int status;
    private String timestamp = ZonedDateTime.now().toOffsetDateTime().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    private List<FieldError> errors;

    private ErrorResponse(ErrorCode errorCode) {
        this.message = errorCode.message();
        this.code = errorCode.code();
        this.status = errorCode.status();
        this.errors = new ArrayList<>();
    }

    public ErrorResponse(ErrorCode errorCode, List<FieldError> fieldErrors) {
        this.message = errorCode.message();
        this.status = errorCode.status();
        this.code = errorCode.code();
        this.errors = fieldErrors;
    }

    public static ErrorResponse valueOf(ErrorCode errorCode, BindingResult bindingResult) {
        return new ErrorResponse(errorCode, buildFieldError(bindingResult));
    }

    public static ErrorResponse valueOf(ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        @Builder
        public FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }
    }

    private static List<ErrorResponse.FieldError> buildFieldError(BindingResult bindingResult) {
        final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
        return fieldErrors.stream()
                .map(error -> ErrorResponse.FieldError.builder()
                        .reason(error.getDefaultMessage())
                        .field(error.getField())
                        .value(error.getRejectedValue().toString())
                        .build())
                .collect(Collectors.toList());
    }

}


