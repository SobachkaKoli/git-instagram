package com.example.sukagram.serviceImpl;

import com.example.sukagram.Exception.*;
import com.example.sukagram.model.ContentType;
import com.example.sukagram.model.FriendShip;
import com.example.sukagram.model.User;
import com.example.sukagram.repository.FriendShipRepository;
import com.example.sukagram.repository.UserRepository;
import com.example.sukagram.service.FriendShipService;
import com.example.sukagram.service.NotificationService;
import com.example.sukagram.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class FriendShipServiceImpl implements FriendShipService{
    private final FriendShipRepository friendShipRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final NotificationService notificationService;


    public FriendShipServiceImpl(FriendShipRepository friendShipRepository, UserService userService, UserRepository userRepository, NotificationService notificationService) {
        this.friendShipRepository = friendShipRepository;
        this.userService = userService;

        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }
    @Override
    public void countFollowersAndFollowing(String followingUserName, String token) throws Status430UserNotFoundException, Status444UserIsNull {
        if (!userRepository.existsByUserName(followingUserName)){
            throw new Status430UserNotFoundException(followingUserName);
        }else{
                User follower = userService.getAuthenticatedUser(token);
                follower.setFollowing(friendShipRepository.countAllByFollower(follower));
                userRepository.save(follower);

               User following = userService.getByUserName(followingUserName);
                following.setFollowers(friendShipRepository.countAllByFollowing(following));
                userRepository.save(following);
        }
    }

    @Override
    public void followUp(String followingUsername, String token) throws Status430UserNotFoundException, Status432SelfFollowingException, Status433FriendShipAlreadyExistsException, Status444UserIsNull {

        if (friendShipRepository.existsByFollowerAndFollowing(
                userService.getAuthenticatedUser(token),userService.getByUserName(followingUsername))){
            throw new Status433FriendShipAlreadyExistsException(followingUsername);
        }else {
            if(!userService.existsByUsername(followingUsername)){
                throw new Status430UserNotFoundException(followingUsername);
            }else if(userService.getAuthenticatedUser(token).getId().equals(followingUsername)){
                throw new Status432SelfFollowingException("You can not follow up yourself");
            }else {
                FriendShip friendShip = FriendShip
                        .builder()
                        .following(userService.getByUserName(followingUsername))
                        .follower(userService.getAuthenticatedUser(token))
                        .build();
                friendShipRepository.save(friendShip);
                countFollowersAndFollowing(followingUsername,token);
                notificationService.sendNotification(
                        token,followingUsername,userService.getByUserName(followingUsername).getUserName() + " now following you",userService.getAuthenticatedUser(token).getId(), ContentType.FOLLOWER);
            }
        }
    }
    @Override
    public void unFollow(String followingUsername, String token) throws Status442FriendShipDoesntExistsException, Status430UserNotFoundException, Status444UserIsNull {
        if (!userRepository.existsByUserName(followingUsername)) {
            throw new Status430UserNotFoundException(followingUsername);
        } else {
            if (!friendShipRepository.existsByFollowerAndFollowing(userService.getAuthenticatedUser(token),
                    userService.getByUserName(followingUsername)
            )) {
                throw new Status442FriendShipDoesntExistsException(userService.getByUserName(followingUsername).getUserName());
            } else {
                friendShipRepository.deleteFriendShipByFollowerAndFollowing(userService.getAuthenticatedUser(token),
                        userService.getByUserName(followingUsername));
                countFollowersAndFollowing(followingUsername, token);
            }
        }
    }
    @Override
    public List<User> getFollowersByUsername(String username) throws Status430UserNotFoundException, Status442FriendShipDoesntExistsException, Status444UserIsNull {

        if(!userService.existsByUsername(username)){
            throw new Status430UserNotFoundException(username);
        }else {
            User user = userService.getByUserName(username);
            if (!friendShipRepository.existsByFollowing(user)) {
                throw new Status442FriendShipDoesntExistsException(user.getUserName());
            }else {

                List<User> followers = new ArrayList<>();
                for (FriendShip friendShip : friendShipRepository.findAllByFollowing(user)) {
                    followers.add(friendShip.getFollower());
                }
                return followers;
            }
        }
    }




    @Override
    public List<User> getFollowingByUsername(String username) throws Status430UserNotFoundException, Status442FriendShipDoesntExistsException, Status444UserIsNull {
        if (!userService.existsByUsername(username)){
            throw new Status430UserNotFoundException(username);
        }else {
            User user = userService.getByUserName(username);
            if(!friendShipRepository.existsByFollower(user)){
                throw new Status442FriendShipDoesntExistsException(user.getUserName());
            }
            List<User> following = new ArrayList<>();
            friendShipRepository.findAllByFollower(user).forEach(friendShip -> following.add(friendShip.getFollowing()));
            return following;
        }
    }
    @Override
    public void deleteFollower(String followingUsername, String token) throws Status430UserNotFoundException, Status444UserIsNull {
        if (friendShipRepository.existsByFollowerAndFollowing(
                userService.getByUserName(followingUsername),userService.getAuthenticatedUser(token))){
            friendShipRepository.deleteFriendShipByFollowerAndFollowing(
                    userService.getByUserName(followingUsername),userService.getAuthenticatedUser(token));
           countFollowersAndFollowing(followingUsername,token);
        }else {
            throw new Status430UserNotFoundException(followingUsername);
        }
    }
}
