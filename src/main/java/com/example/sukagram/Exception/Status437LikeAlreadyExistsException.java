package com.example.sukagram.Exception;

public class Status437LikeAlreadyExistsException extends ErrorCodeException{
    public static final int CODE = 437;

    public Status437LikeAlreadyExistsException(String message) {
        super(CODE,"This: " + message + " is already Like"," 437 ");
    }
}
