package com.example.sukagram.Exception;

public class Status436UserNotCommentAuthorException extends ErrorCodeException{

    public static final int CODE = 436;

    public Status436UserNotCommentAuthorException(String message) {
        super(CODE,"You are not author this comment : " + message," 436 ");
    }
}
