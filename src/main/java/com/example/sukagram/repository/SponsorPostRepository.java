package com.example.sukagram.repository;

import com.example.sukagram.model.SponsorPost;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SponsorPostRepository extends MongoRepository<SponsorPost,String> {
}
