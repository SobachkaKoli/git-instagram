package com.example.sukagram.Exception;

public class Status435UserNotPostAuthorException extends ErrorCodeException{
    public static final int CODE = 435;

    public Status435UserNotPostAuthorException(String message) {
        super(CODE,"You are not author this post : " + message," 435 ");
    }
}
