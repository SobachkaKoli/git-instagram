package com.example.sukagram.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.sukagram.DTO.UserDTO;
import com.example.sukagram.Exception.Status434UserNicknameNotUniqueException;
import com.example.sukagram.Exception.Status444UserIsNull;
import com.example.sukagram.model.User;
import com.example.sukagram.notifications.Notification;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User registerUser(UserDTO userDTO) throws Status434UserNicknameNotUniqueException;

    User getByUserName(String username) throws Status444UserIsNull;
    User getAuthenticatedUser(String token) throws Status444UserIsNull;

    User deleteMyAccount(String token) throws Exception;

    List<Notification> getMyNotifications(String token) throws Status444UserIsNull;
    String validateToken(String token);

    User save(User user);

    List<User> getUsers();

    void  refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;


    boolean existsByUsername(String username);

    DecodedJWT decodedJWT(String token);

}
