package com.example.sukagram.repository;

import com.example.sukagram.model.FriendShip;
import com.example.sukagram.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface FriendShipRepository extends MongoRepository<FriendShip,String>{

    boolean existsByFollowerAndFollowing(User follower, User following);

    boolean existsByFollowing(User following);



    boolean existsByFollowingId(String followingId);
    boolean existsByFollower(User follower);
    void    deleteFriendShipByFollowerAndFollowing(User follower, User following);

    int countAllByFollower(User follower);
    int countAllByFollowing(User following);

    List<FriendShip> findAllByFollowing(User following);

    List<FriendShip> findAllByFollower(User follower);
}
