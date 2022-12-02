package com.example.sukagram.repository;

import com.example.sukagram.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends MongoRepository<User,String> {

    Optional<User> findByUserName(String userName);

    boolean existsByUserName(String userName);

    void deleteByUserName(String userName);
}
