package com.example.sukagram.service;

import com.example.sukagram.DTO.CommentDTO;
import com.example.sukagram.Exception.Status436UserNotCommentAuthorException;
import com.example.sukagram.Exception.Status439CommentNotFoundException;
import com.example.sukagram.Exception.Status440PostNotFoundException;
import com.example.sukagram.Exception.Status444UserIsNull;
import com.example.sukagram.model.Comment;

public interface CommentService {


    Comment addComment(CommentDTO commentDTO, String postId, String token) throws Status440PostNotFoundException, Status444UserIsNull;

    void deleteComment(String commentId, String token) throws Status436UserNotCommentAuthorException, Status439CommentNotFoundException, Status444UserIsNull;

    boolean userIsCommentAuthor(String commentId, String token) throws Status444UserIsNull;
    Comment save(Comment comment);
    boolean existsById(String id);
    void deleteAllByPostId(String postId);
    Comment getById(String commentId);
}
