package com.example.sukagram.Exception;

public class Status434UserNicknameNotUniqueException extends ErrorCodeException{
    public static final int CODE = 434;

    public Status434UserNicknameNotUniqueException() {
        super(CODE,"This nickname is already taken","434");
    }
}
