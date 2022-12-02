package com.example.sukagram.Exception;

public class Status433FriendShipAlreadyExistsException extends ErrorCodeException{
    public static final int CODE = 433;

    public Status433FriendShipAlreadyExistsException(String message) {
        super(CODE, "You are already follow up by user with ID: " + message,"432");
    }
}
