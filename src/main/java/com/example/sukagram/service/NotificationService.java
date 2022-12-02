package com.example.sukagram.service;

import com.example.sukagram.Exception.Status444UserIsNull;
import com.example.sukagram.model.ContentType;

public interface NotificationService {

    void sendNotification(String token, String recipient, String message, String documentId, ContentType contentType) throws Status444UserIsNull;

}
