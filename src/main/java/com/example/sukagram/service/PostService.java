package com.example.sukagram.service;

import com.example.sukagram.DTO.PostDTO;
import com.example.sukagram.Exception.*;
import com.example.sukagram.model.Comment;
import com.example.sukagram.model.Post;
import com.example.sukagram.model.SponsorPost;
import com.example.sukagram.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PostService {
    Post createPost(PostDTO postDTO,String token) throws IOException, Status443FileIsNullException, Status444UserIsNull;

    SponsorPost createSponsorPost(PostDTO postDTO, String sponsorName, String token) throws IOException, Status443FileIsNullException, Status430UserNotFoundException, Status444UserIsNull;
    Post getPostById(String postId) throws Status440PostNotFoundException;
    void deletePostById(String postId,String token) throws Status435UserNotPostAuthorException, Status440PostNotFoundException, Status444UserIsNull;
    Post save(Post post);
    Post updatePost(PostDTO postDTO, String postId, String token) throws Status435UserNotPostAuthorException, Status440PostNotFoundException, IOException, Status443FileIsNullException, Status444UserIsNull, Status445PictureIsNull;
    boolean userIsPostAuthor(String postId, String token) throws Exception;

    List<Post> getAllByAuthenticated(String token) throws Exception;

    List<Comment> getCommentsByPostId(String postId) throws Status440PostNotFoundException;
    boolean existsById(String postId);



    List<Post> getAllByUsername(String username) throws Status444UserIsNull;
  List<Post> findAll();

}
