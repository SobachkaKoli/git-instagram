package com.example.sukagram.controller;

import com.example.sukagram.DTO.CommentDTO;
import com.example.sukagram.DTO.PostDTO;
import com.example.sukagram.Exception.*;
import com.example.sukagram.model.Comment;
import com.example.sukagram.model.Like;
import com.example.sukagram.model.Post;
import com.example.sukagram.model.SponsorPost;
import com.example.sukagram.service.CommentService;
import com.example.sukagram.service.LikeService;
import com.example.sukagram.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/user")
public class PostController {
    private final PostService postService;
    private final LikeService likeService;
    private final CommentService commentService;
    @Autowired
    public PostController(PostService postService, LikeService likeService, CommentService commentService) {
        this.postService = postService;
        this.likeService = likeService;

        this.commentService = commentService;
    }

    @GetMapping("/get-posts")
    private List<Post> getPosts(){return postService.findAll();}

    @GetMapping("/get-my-posts")
    private List<Post> getPostsAuthenticatedUser(@RequestHeader("Authorization") String token) throws Exception {
        return postService.getAllByAuthenticated(token);}

    @GetMapping("/get-user-posts/{username}")
    private List<Post> getPostsUser(@PathVariable String username) throws Status444UserIsNull {
        return postService.getAllByUsername(username);}

    @GetMapping("/get-post-comments/{postId}")
    private List<Comment> getPostComments(@PathVariable String postId) throws Status440PostNotFoundException {
        return postService.getCommentsByPostId(postId);
    }

    @PostMapping("/create-post")
    private Post createPost(@ModelAttribute PostDTO postDTO,@RequestHeader("Authorization") String token) throws IOException, Status443FileIsNullException, Status444UserIsNull {
        return postService.createPost(postDTO,token);
    }
    @PostMapping("/create-sponsor-post/{sponsorName}")
    private SponsorPost createSponsorPost(@ModelAttribute PostDTO postDTO,
                                                          @RequestHeader("Authorization") String token,
                                                          @PathVariable String sponsorName) throws IOException, Status443FileIsNullException, Status430UserNotFoundException, Status444UserIsNull {
        return postService.createSponsorPost(postDTO,sponsorName,token);
    }

    @PatchMapping("/update-post/{postId}")
    public Post updatePost(@ModelAttribute PostDTO postDTO,@PathVariable String postId,@RequestHeader("Authorization") String token)
            throws Status435UserNotPostAuthorException, Status440PostNotFoundException, IOException, Status443FileIsNullException, Status444UserIsNull, Status445PictureIsNull {
        return postService.updatePost(postDTO,postId,token);
    }
//    @GetMapping("/get-comments-post/{postId}")
//    private ResponseEntity<List<Comment>> getPostComments(@PathVariable String postId){
//        return ResponseEntity.ok().body(commentService.getPostComments(postId));
//    }


    @PostMapping("/post/like/{postId}")
    private void setLikeToPost(@PathVariable String postId, @RequestHeader("Authorization") String token)
            throws Status437LikeAlreadyExistsException, Status440PostNotFoundException, Status444UserIsNull {
        likeService.likePost(postId,token);
    }

    @DeleteMapping("/post/unlike/{postId}")
    public void unsetLikeToPost(@PathVariable String postId, @RequestHeader("Authorization") String token) throws Status438LikeNotFoundException, Status440PostNotFoundException, Status444UserIsNull {
        likeService.unLikePost(postId, token);
    }

    @PostMapping("/post/addComment/{postId}")
    public Comment addComment(@RequestBody CommentDTO commentDTO,
                                              @PathVariable String postId,
                                              @RequestHeader("Authorization") String token) throws Status440PostNotFoundException, Status444UserIsNull {
        return commentService.addComment(commentDTO,postId,token);

    }

    @DeleteMapping("/post/delete/{id}")
    private void deletePost(@PathVariable String id, @RequestHeader("Authorization") String token) throws Status435UserNotPostAuthorException, Status440PostNotFoundException, Status444UserIsNull {
        postService.deletePostById(id,token);
    }

}
