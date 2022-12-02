package com.example.sukagram.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;



import java.util.Date;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "posts")
public class Post {
    @Id
    private String id;
    private List<String> picturePath;
    @DBRef
    private User author;
    private String text;
    private int countLikes;
    private int countComment;
    private ContentType contentType;

    @CreatedDate
    private Date pubDate;
    @JsonIgnore
    //TODO почитати лейзі і ігр
    @DBRef(lazy = true)
    private List<Comment> comments;


}
