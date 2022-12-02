package com.example.sukagram.notifications;

import com.example.sukagram.DTO.CommentDTO;
import com.example.sukagram.model.ContentType;
import com.example.sukagram.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "notifications")
public class Notification {
    @Id
    private String id;
    @DBRef
    private User sender;
    @DBRef
    private User recipient;
    private String message;
    private String documentId;
    private ContentType contentType;
    @CreatedDate
    private Date sendDate;

}
