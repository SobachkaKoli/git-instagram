package com.example.sukagram.controller;


import com.example.sukagram.DTO.UserDTO;
import com.example.sukagram.Exception.*;
import com.example.sukagram.model.*;
import com.example.sukagram.notifications.Notification;
import com.example.sukagram.service.AvatarService;
import com.example.sukagram.service.FriendShipService;
import com.example.sukagram.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.*;


import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final FriendShipService friendShipService;
    private final AvatarService avatarService;

    @Autowired
    public UserController(UserService userService, FriendShipService friendShipService, AvatarService avatarService) {
        this.userService = userService;
        this.friendShipService = friendShipService;
        this.avatarService = avatarService;
    }

    @GetMapping("/users")
    public List<User> getAll(){return userService.getUsers();}

    @PostMapping("/registration")
    public User saveUser(@RequestBody UserDTO userDTO) throws Status434UserNicknameNotUniqueException {
        return userService.registerUser(userDTO);

    }

    @PostMapping("/set-avatar")
    public String uploadAvatar(@RequestParam("file") MultipartFile file,
                                               @RequestHeader("Authorization") String token) throws IOException, Status443FileIsNullException, Status444UserIsNull {
        return avatarService.setAvatar(file, token);
    }

    @GetMapping("/get-user-by-username/{username}")
    public User getUserByUsername(@PathVariable String username) throws Status444UserIsNull {
        return userService.getByUserName(username);
    }


    @PostMapping("/follow-up/{followingUsername}")
    public void followUp(@PathVariable String followingUsername, @RequestHeader("Authorization") String  token) throws Status430UserNotFoundException, Status433FriendShipAlreadyExistsException, Status432SelfFollowingException, Status444UserIsNull {
        friendShipService.followUp(followingUsername, token);
    }

    @GetMapping("/get-notifications")
    public List<Notification> getMyNotifications(@RequestHeader("Authorization") String token) throws Status444UserIsNull {
        return  userService.getMyNotifications(token);
    }

    @DeleteMapping("/user/unfollow-up/{followingUsername}")
    public void unFollow(@PathVariable String followingUsername, @RequestHeader("Authorization") String  token) throws Status430UserNotFoundException, Status442FriendShipDoesntExistsException, Status444UserIsNull {
        friendShipService.unFollow(followingUsername, token);
    }

    @PatchMapping("/delete-account")
    public User deleteAccount(@RequestHeader("Authorization") String token) throws Exception {
        return userService.deleteMyAccount(token);
    }

    @DeleteMapping("/delete-follower/{followerUsername}")
    public void deleteFollower(@PathVariable String followerUsername, @RequestHeader("Authorization") String  token) throws Status430UserNotFoundException, Status442FriendShipDoesntExistsException, Status444UserIsNull {
        friendShipService.deleteFollower(followerUsername, token);
    }

    @GetMapping("/get-followers-user/{username}")
    public List<User> getFollowersByUserId(@PathVariable String username) throws Status430UserNotFoundException, Status442FriendShipDoesntExistsException, Status444UserIsNull {
        return friendShipService.getFollowersByUsername(username);
    }

    @GetMapping("/get-following-user/{username}")
    public List<User> getFollowingByUsername(@PathVariable String username) throws Status430UserNotFoundException, Status442FriendShipDoesntExistsException, Status444UserIsNull {
        return friendShipService.getFollowingByUsername(username);
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
       userService.refreshToken(request,response);
    }




}

