package com.example.sukagram.Exception;

public class Status442FriendShipDoesntExistsException extends ErrorCodeException{
    private static final int CODE = 442;

    public Status442FriendShipDoesntExistsException(String message) {
        super(CODE, "Friendship with: " + message + " doesnt exists","442");
    }
}
