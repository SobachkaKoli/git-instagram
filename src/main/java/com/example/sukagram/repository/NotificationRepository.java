package com.example.sukagram.repository;

import com.example.sukagram.model.User;
import com.example.sukagram.notifications.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification,String > {
    List<Notification> findAllByRecipient(User recipient);
}
