package com.example.sukagram.Exception;

public class Status430UserNotFoundException extends ErrorCodeException{
    public static final int CODE = 430;

    public Status430UserNotFoundException(String message) {
        super(CODE, "user with id:"+ message + " not found", "430");
    }
}
