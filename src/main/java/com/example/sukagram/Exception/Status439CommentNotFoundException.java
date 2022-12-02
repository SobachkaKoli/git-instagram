package com.example.sukagram.Exception;

public class Status439CommentNotFoundException extends ErrorCodeException{
    public static final int CODE = 439;
    public Status439CommentNotFoundException(String message) {
        super(CODE,"comment with id : " + message + " not found ","439");
    }
}
