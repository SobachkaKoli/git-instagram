package com.example.sukagram.service;

import com.example.sukagram.Exception.*;
import com.example.sukagram.model.ContentType;

public interface LikeService {
    void likePost(String postId,String token ) throws Status444UserIsNull, Status440PostNotFoundException, Status437LikeAlreadyExistsException;
    void unLikePost(String postId,String token ) throws Status444UserIsNull, Status440PostNotFoundException, Status438LikeNotFoundException;

    void likeComment(String commentId,String token ) throws Status444UserIsNull, Status440PostNotFoundException, Status437LikeAlreadyExistsException, Status439CommentNotFoundException;
    void unLikeComment(String commentId,String token ) throws Status444UserIsNull, Status440PostNotFoundException, Status439CommentNotFoundException, Status438LikeNotFoundException;
    void deleteAllByDocumentId(String documentId);
}
