package com.wan.todo.common.exception;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    private String message;
    private String code;
    private int status;
    private String timestamp = ZonedDateTime.now().toOffsetDateTime().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    private List<FieldError> errors;
    private Detail detail;

    @Builder
    public ErrorResponse(String message, String code, int status, List<FieldError> errors, Detail detail) {
        this.message = message;
        this.code = code;
        this.status = status;
        this.errors = errors == null ? new ArrayList<>() : errors;
        this.detail = detail;
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

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Detail<T> {
        private String className;
        private String methodName;
        private long lineNumber;
        private String exceptionName;
        private String errorMessage;
        private String path;
        private String httpMethod;
        private T value;


        @Builder
        public Detail(String className, String methodName, long lineNumber, String exceptionName, String errorMessage, String path, String httpMethod, T value) {
            this.className = className;
            this.methodName = methodName;
            this.lineNumber = lineNumber;
            this.exceptionName = exceptionName;
            this.errorMessage = errorMessage;
            this.path = path;
            this.httpMethod = httpMethod;
            this.value = hasDefaultConstructor(value) ? value : (T) new ValueEmpty();
        }


        // 기본 생성자가 없는 경우 Deserialize 진행하지 못하니 ValueEmpty 처리
        private boolean hasDefaultConstructor(T value) {
            try {
                final Class<?> valueClass = value.getClass();
                valueClass.getDeclaredConstructor();
                return true;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            return false;
        }

        @Getter
        private static class ValueEmpty {
            private final String message = "There is no default constructor.";
        }
    }

}


