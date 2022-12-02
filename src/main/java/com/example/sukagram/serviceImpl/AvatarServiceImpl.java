package com.example.sukagram.serviceImpl;


import com.example.sukagram.Exception.Status443FileIsNullException;
import com.example.sukagram.Exception.Status444UserIsNull;
import com.example.sukagram.model.User;
import com.example.sukagram.repository.UserRepository;
import com.example.sukagram.service.AvatarService;
import com.example.sukagram.service.PictureService;
import com.example.sukagram.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AvatarServiceImpl implements AvatarService {

    @Value("${upload.path}")
    private String uploadPath;

    private final UserService userService;
    private final PictureService pictureService;
    private final UserRepository userRepository;

    public AvatarServiceImpl(UserService userService, PictureService pictureService, UserRepository userRepository) {
        this.userService = userService;
        this.pictureService = pictureService;
        this.userRepository = userRepository;
    }

    @Override
    public String setAvatar(MultipartFile file, String token) throws IOException, Status443FileIsNullException, Status444UserIsNull {

        String picturePath = pictureService.savePicture(
                file,pictureService.createAvatarPath(token));

        User user = userRepository.findByUserName(userService.getAuthenticatedUser(token).getUserName()).orElseThrow();
//
        user.setAvatar(picturePath);
        userService.save(user);
        return picturePath;
    }
}
