package com.example.sukagram.repository;

import com.example.sukagram.model.Like;
import com.example.sukagram.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends MongoRepository<Like,String> {
    boolean existsByDocumentIdAndAuthor(String documentId, User user);
    int countLikeByDocumentId(String documentId);
    void deleteLikeByDocumentIdAndAuthor(String documentId, User author);
    void deleteAllByDocumentId(String documentId);

}
