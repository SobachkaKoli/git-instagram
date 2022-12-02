package com.example.sukagram.repository;

import com.example.sukagram.model.Post;
import com.example.sukagram.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends MongoRepository<Post,String> {





    List<Post> findAllByAuthorId(String authorId);

    List<Post> findAllByAuthor(User user);

    List<Post> findAll();

}
