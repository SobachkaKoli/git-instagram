package com.example.sukagram.Exception;

public class Status445PictureIsNull extends ErrorCodeException{
    private static final int CODE = 445;
    public Status445PictureIsNull(String message) {
        super(CODE, message,"445");
    }
}
