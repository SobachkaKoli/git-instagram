package com.example.sukagram.service;

import com.example.sukagram.Exception.*;
import com.example.sukagram.model.User;

import java.util.List;

public interface FriendShipService {
   void followUp(String followingUsername, String token) throws Status430UserNotFoundException, Status432SelfFollowingException, Status433FriendShipAlreadyExistsException, Status444UserIsNull;

   void unFollow(String followingUsername , String token) throws Status430UserNotFoundException, Status442FriendShipDoesntExistsException, Status444UserIsNull;

   List<User> getFollowersByUsername(String userId) throws Status430UserNotFoundException, Status442FriendShipDoesntExistsException, Status444UserIsNull;
   List<User> getFollowingByUsername(String userId) throws Status430UserNotFoundException, Status442FriendShipDoesntExistsException, Status444UserIsNull;

   void countFollowersAndFollowing(String followingId, String token) throws Status430UserNotFoundException, Status444UserIsNull;
   void deleteFollower(String followerUsername, String token) throws Status430UserNotFoundException, Status444UserIsNull;

}
