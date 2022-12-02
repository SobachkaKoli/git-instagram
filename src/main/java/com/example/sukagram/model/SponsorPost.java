package com.example.sukagram.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "sponsorPosts")
public class SponsorPost{

    @Id
    private String id;
    @DBRef
    private User sponsor;
    @DBRef
    private Post post;
}
