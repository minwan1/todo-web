package com.wan.todo.common.exception;

import javax.servlet.http.HttpServletRequest;

class ErrorDetailBuilder {

    static ErrorResponse.Detail newType(Exception e, HttpServletRequest request) {

        final StackTraceElement[] stackTrace = e.getStackTrace();
        final String exceptionName = e.getClass().getSimpleName();
        final String errorMessage = e.getMessage();
        final String path = request.getServletPath();
        final String httpMethod = request.getMethod();

        if (stackTrace.length != 0) {
            final StackTraceElement stackTraceElement = stackTrace[0];
            String className = stackTraceElement.getClassName();
            String methodName = stackTraceElement.getMethodName();
            int lineNumber = stackTraceElement.getLineNumber();
            return ErrorDetail(exceptionName, errorMessage, path, httpMethod, className, methodName, lineNumber);
        }

        return ErrorDetail(exceptionName, errorMessage, path, httpMethod);
    }

    static ErrorResponse.Detail newType(Exception e, HttpServletRequest request, Object value) {
        final StackTraceElement[] stackTrace = e.getStackTrace();
        final String exceptionName = e.getClass().getSimpleName();
        final String errorMessage = e.getMessage();
        final String path = request.getServletPath();
        final String httpMethod = request.getMethod();

        if (stackTrace.length != 0) {
            final StackTraceElement stackTraceElement = stackTrace[0];
            String className = stackTraceElement.getClassName();
            String methodName = stackTraceElement.getMethodName();
            int lineNumber = stackTraceElement.getLineNumber();
            return ErrorDetail(exceptionName, errorMessage, path, httpMethod, className, methodName, lineNumber, value);
        }

        return ErrorDetail(exceptionName, errorMessage, path, httpMethod);
    }


    private static ErrorResponse.Detail ErrorDetail(String exceptionName, String message, String path, String method, String className, String methodName, int lineNumber) {
        return ErrorResponse.Detail.builder()
                .className(className)
                .methodName(methodName)
                .lineNumber(lineNumber)
                .exceptionName(exceptionName)
                .errorMessage(message)
                .httpMethod(method)
                .path(path)
                .build();
    }

    private static ErrorResponse.Detail ErrorDetail(String exceptionName, String message, String path, String method, String className, String methodName, int lineNumber, Object value) {
        return ErrorResponse.Detail.builder()
                .className(className)
                .methodName(methodName)
                .lineNumber(lineNumber)
                .exceptionName(exceptionName)
                .errorMessage(message)
                .httpMethod(method)
                .value(value)
                .path(path)
                .build();
    }

    private static ErrorResponse.Detail ErrorDetail(String exceptionName, String message, String path, String httpMethod) {
        return ErrorResponse.Detail.builder()
                .exceptionName(exceptionName)
                .errorMessage(message)
                .path(path)
                .httpMethod(httpMethod)
                .build();
    }

}
