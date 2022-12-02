package com.example.sukagram.Exception;

public class Status432SelfFollowingException extends ErrorCodeException{
    public static final int CODE = 432;
    public Status432SelfFollowingException(String message) {
        super(CODE, message,"432");
    }
}
