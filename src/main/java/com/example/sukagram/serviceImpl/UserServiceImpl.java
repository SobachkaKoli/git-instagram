package com.example.sukagram.serviceImpl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.sukagram.DTO.UserDTO;
import com.example.sukagram.Exception.Status434UserNicknameNotUniqueException;
import com.example.sukagram.Exception.Status444UserIsNull;
import com.example.sukagram.model.User;
import com.example.sukagram.model.Role;
import com.example.sukagram.notifications.Notification;
import com.example.sukagram.repository.NotificationRepository;
import com.example.sukagram.repository.UserRepository;
import com.example.sukagram.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService{

    @Value("${upload.path}")
    private String uploadPath;
    private final UserRepository userRepository;

    private final NotificationRepository notificationRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, NotificationRepository notificationRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(UserDTO userDTO) throws Status434UserNicknameNotUniqueException {
        if (userRepository.existsByUserName(userDTO.getUserName())) {
            throw new Status434UserNicknameNotUniqueException();
        }

        User user = User.builder()
                .email(userDTO.getEmail())
                .userName(userDTO.getUserName())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(Role.USER)
                .build();

        File userDir = new File(uploadPath + "/" + user.getUserName());
        userDir.mkdir();

        File userAvatarsDir = new File(uploadPath + "/" + user.getUserName() + "/avatars");
        userAvatarsDir.mkdir();

        File userPostsDir = new File(uploadPath + "/" + user.getUserName() + "/posts");
        userPostsDir.mkdir();


        log.info("Saving new user {} to the database", user.getUserName());
        System.out.println(user);

        return userRepository.save(user);
    }

    @Override
    public User getByUserName(String username) throws Status444UserIsNull {
        return userRepository.findByUserName(username).orElseThrow(() -> new Status444UserIsNull(username + " Not found"));
    }

    //TODO ByUSERNAME
    @Override
    public User getAuthenticatedUser(String token) throws Status444UserIsNull {
        return getByUserName(JWT.decode(validateToken(token)).getSubject());
    }

    @Override
    public User deleteMyAccount(String token) throws Exception {
       // List<Comment> comments = commentRepository.findAllByAuthor(getAuthenticatedUser(token).get());
        User user = getAuthenticatedUser(token);

        File avatar = new File(user.getAvatar());
        avatar.delete();
        user.setUserName("DELETED");
        user.setAvatar("");
        user.setEmail("");
        user.setRole(Role.DELETED);
        user.setPassword("");
        user.setFollowers(0);
        user.setFollowing(0);
        return userRepository.save(user);
    }

    @Override
    public List<Notification> getMyNotifications(String token) throws Status444UserIsNull {
        return notificationRepository.findAllByRecipient(getAuthenticatedUser(token));
    }

    public String validateToken(String token){
        return token.substring(7);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching users");
        return userRepository.findAll();
    }

    public  void  refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = getByUserName(username);
                String access_token = JWT.create()
                        .withSubject(user.getUserName())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
//                        .withClaim("roles",user.get().getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token",access_token);
                tokens.put("refresh_token",refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(),tokens);
            }catch (Exception exception){
                response.setHeader("error",exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                //  response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message",exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(),error);
            }

        }else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUserName(username);
    }

    @Override
    public DecodedJWT decodedJWT(String token) {
        Base64.getDecoder().decode(JWT.decode(token).getSubject());
        JWT.decode(token);
        return JWT.decode(token);
    }
}
