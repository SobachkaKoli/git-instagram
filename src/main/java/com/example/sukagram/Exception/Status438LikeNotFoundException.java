package com.example.sukagram.Exception;

public class Status438LikeNotFoundException extends ErrorCodeException{
    public static final int CODE = 438;


    public Status438LikeNotFoundException(String message) {
        super(CODE, "This " + message + " has no likes" ,"438");
    }
}
