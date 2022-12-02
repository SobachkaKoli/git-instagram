package com.example.sukagram.Exception;

public class Status443FileIsNullException extends ErrorCodeException{
    private static final int CODE = 443;
    public Status443FileIsNullException( String message) {
        super(CODE, message,"442");
    }
}
