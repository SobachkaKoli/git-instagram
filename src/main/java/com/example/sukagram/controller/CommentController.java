package com.example.sukagram.controller;

import com.example.sukagram.Exception.*;
import com.example.sukagram.model.Like;
import com.example.sukagram.service.CommentService;
import com.example.sukagram.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/user")
public class CommentController {

    private final CommentService commentService;
    private final LikeService likeService;
    @Autowired
    public CommentController(CommentService commentService,LikeService likeService) {
        this.commentService = commentService;
        this.likeService = likeService;
    }

    @DeleteMapping("/comment/delete/{commentId}")
    public void deleteComment(@PathVariable String commentId, @RequestHeader("Authorization") String token) throws Status436UserNotCommentAuthorException, Status439CommentNotFoundException, Status444UserIsNull {
        commentService.deleteComment(commentId, token);
    }
    
    @PostMapping("/comment/set-like/{commentId}")
    public void setLikeToComment(@PathVariable String commentId, @RequestHeader("Authorization") String token) throws Status437LikeAlreadyExistsException, Status439CommentNotFoundException, Status444UserIsNull, Status440PostNotFoundException {
        likeService.likeComment(commentId,token);
    }

    @DeleteMapping("/comment/unset-like/{commentId}")
    public void unsetLikeToComment(@PathVariable String commentId, @RequestHeader("Authorization") String token) throws Status438LikeNotFoundException, Status439CommentNotFoundException, Status444UserIsNull, Status440PostNotFoundException {
        likeService.unLikeComment(commentId,token);
    }

}
