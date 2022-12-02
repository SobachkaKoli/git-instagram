package com.example.sukagram.notifications;

import com.example.sukagram.Exception.Status444UserIsNull;
import com.example.sukagram.model.ContentType;
import com.example.sukagram.repository.NotificationRepository;
import com.example.sukagram.repository.UserRepository;
import com.example.sukagram.service.NotificationService;
import com.example.sukagram.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final UserService userService;

    public NotificationServiceImpl(NotificationRepository notificationRepository, UserService userService) {
        this.notificationRepository = notificationRepository;
        this.userService = userService;
    }
    @Override
    public void sendNotification(String token, String recipientUsername, String message, String documentId, ContentType contentType) throws Status444UserIsNull {
        Notification notification = Notification.builder()
                .sender(userService.getAuthenticatedUser(token))
                .message(message)
                .documentId(documentId)
                .contentType(contentType)
                .recipient(userService.getByUserName(recipientUsername))
                .build();
        notificationRepository.save(notification);
    }

}
