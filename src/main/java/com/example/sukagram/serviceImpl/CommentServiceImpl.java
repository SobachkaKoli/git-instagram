package com.example.sukagram.serviceImpl;

import com.example.sukagram.DTO.CommentDTO;
import com.example.sukagram.Exception.Status436UserNotCommentAuthorException;
import com.example.sukagram.Exception.Status439CommentNotFoundException;
import com.example.sukagram.Exception.Status440PostNotFoundException;
import com.example.sukagram.Exception.Status444UserIsNull;
import com.example.sukagram.model.ContentType;
import com.example.sukagram.model.Role;
import com.example.sukagram.model.Comment;
import com.example.sukagram.model.Post;
import com.example.sukagram.repository.CommentRepository;
import com.example.sukagram.repository.PostRepository;
import com.example.sukagram.service.CommentService;
import com.example.sukagram.service.NotificationService;
import com.example.sukagram.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final CommentRepository commentRepository;
    private final NotificationService notificationService;


    public CommentServiceImpl(PostRepository postRepository, UserService userService, CommentRepository commentRepository, NotificationService notificationService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.commentRepository = commentRepository;
        this.notificationService = notificationService;
    }

    @Override
    public Comment addComment(CommentDTO commentDTO, String postId, String token) throws Status440PostNotFoundException, Status444UserIsNull {
        if(postRepository.existsById(postId)) {
            Post post = postRepository.findById(postId).orElseThrow(()-> new Status440PostNotFoundException(postId));
//            Post post = postRepository.getPostById(postId).orElseThrow();
            Comment comment = Comment.builder()
                    .text(commentDTO.getText())
                    .author(userService.getAuthenticatedUser(token))
                    .contentType(ContentType.COMMENT)
                    .post(post)
                    .build();
            post.getComments().add(comment);
            commentRepository.save(comment);
            notificationService.sendNotification(token,
                    post.getAuthor().getId(),
                    "New comment to the post from " + userService.getAuthenticatedUser(token).getUserName(),postId,ContentType.COMMENT);
            post.setCountComment(post.getCountComment() + 1);
            postRepository.save(post);
            return comment;
        }else{
            throw new Status440PostNotFoundException(postId);
        }
    }

    @Override
    public void deleteComment(String commentId, String token) throws Status436UserNotCommentAuthorException, Status439CommentNotFoundException, Status444UserIsNull {
        if (commentRepository.existsById(commentId)) {
            if (userIsCommentAuthor(commentId,token) | userService.getAuthenticatedUser(token).getRole().equals(Role.ADMIN)) {
                commentRepository.deleteById(commentId);
            } else {
                throw new Status436UserNotCommentAuthorException(commentId);
            }
        }else {
            throw new Status439CommentNotFoundException(commentId);
        }
    }

    @Override
    public boolean userIsCommentAuthor(String commentId, String token) throws Status444UserIsNull {
        String userId = userService.getAuthenticatedUser(token).getId();
        String authorId = commentRepository.findById(commentId).get().getAuthor().getId();
        return authorId.equals(userId);
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public boolean existsById(String commentId) {
        return commentRepository.existsById( commentId);
    }

//    @Override
//    public List<Comment> getPostComments(String postId) {
//        return commentRepository.getCommentsByPostId(postId);
//    }

    @Override
    public void deleteAllByPostId(String postId) {
        commentRepository.deleteAllByPostId(postId);
    }
    @Override
    public Comment getById(String commentId) {
        return commentRepository.getCommentById(commentId);
    }
}
