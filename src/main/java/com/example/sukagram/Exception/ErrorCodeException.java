package com.example.sukagram.Exception;

import org.springframework.stereotype.Component;

@Component
public class ErrorCodeException extends Exception {
    private static final String GENERAL_EXCEPTION_GUID = "ErrorCodeException";
    private int code = Integer.MAX_VALUE;
    private Object context;
    private String errorCode = GENERAL_EXCEPTION_GUID; //Unique string for the exception (used by feign decoder imp)

    private ErrorCodeException() {
        super("Error code exception without message");
    }

    private ErrorCodeException(String message) {
        super(message);
    }

    private ErrorCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    private ErrorCodeException(Throwable cause) {
        super("Error code exception without message", cause);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public ErrorCodeException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ErrorCodeException(int code, String message, final String globallyUniqueErrorIdentifier) {
        super(message);
        this.code = code;
        errorCode = globallyUniqueErrorIdentifier;
    }

    public ErrorCodeException(int code, String message, Object context, final String globallyUniqueErrorIdentifier) {
        super(message);
        this.code = code;
        this.context = context;
        errorCode = globallyUniqueErrorIdentifier;
    }

    public ErrorCodeException(int code, String message, Throwable cause, final String globallyUniqueErrorIdentifier) {
        super(message, cause);
        this.code = code;
        errorCode = globallyUniqueErrorIdentifier;
    }

    public ErrorCodeException(int code, String message, Throwable cause, Object context, final String globallyUniqueErrorIdentifier) {
        super(message, cause);
        this.code = code;
        this.context = context;
        errorCode = globallyUniqueErrorIdentifier;
    }

    public int getCode() {
        return code;
    }

    public Object getContext() {
        return context;
    }

    public ErrorCodeException setContext(Object context) {
        this.context = context;
        return this;
    }

    @Override
    public String toString() {
        return "ErrorCodeException [" +
                "code=" + code +
                ", msg=" + getMessage() +
                ", context=" + context +
                ", errorCode='" + errorCode + '\'' +
                ']';
    }
}
