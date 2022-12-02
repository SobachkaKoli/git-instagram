package com.example.sukagram.Exception;

public class Status440PostNotFoundException extends ErrorCodeException{

    private static final int CODE = 440;
    public Status440PostNotFoundException(String message) {
        super(CODE, "Post with id: " + message + " not found","440");
    }
}
