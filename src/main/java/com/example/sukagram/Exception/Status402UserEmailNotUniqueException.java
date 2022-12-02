package com.example.sukagram.Exception;



public class Status402UserEmailNotUniqueException  extends ErrorCodeException {
    public static final int CODE = 402;

    public Status402UserEmailNotUniqueException(String message) {
        super(CODE, "email:"+ message + " already exist", "402");
    }
}
