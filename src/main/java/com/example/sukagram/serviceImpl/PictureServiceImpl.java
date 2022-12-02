package com.example.sukagram.serviceImpl;


import com.example.sukagram.Exception.Status443FileIsNullException;
import com.example.sukagram.Exception.Status444UserIsNull;
import com.example.sukagram.service.PictureService;
import com.example.sukagram.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class PictureServiceImpl implements PictureService {

    private final UserService userService;

    @Value("${upload.path}")
    private String uploadPath;

    public PictureServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String savePicture(MultipartFile file,String dir) throws IOException, Status443FileIsNullException {

        if (file != null && !file.getOriginalFilename().isEmpty()){
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            String imgPath = dir + resultFileName;
            file.transferTo(new File(imgPath));
            return imgPath;
        }else {
            throw new Status443FileIsNullException("File is null");
        }
    }

    @Override
    public String createPostPicturePath(String token) throws Status444UserIsNull {
        return uploadPath + "/" + userService.getAuthenticatedUser(token).getUserName()
                + "/posts/";
    }

    @Override
    public String createAvatarPath(String token) throws Status444UserIsNull {
         return uploadPath + "/" + userService.getAuthenticatedUser(token).getUserName()
                + "/avatars/";
    }


}
