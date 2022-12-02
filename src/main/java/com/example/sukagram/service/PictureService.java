package com.example.sukagram.service;

import com.example.sukagram.Exception.Status443FileIsNullException;
import com.example.sukagram.Exception.Status444UserIsNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PictureService {

    String savePicture(MultipartFile multipartFile,String dir) throws IOException, Status443FileIsNullException;

    String createPostPicturePath(String token) throws Status444UserIsNull;
    String createAvatarPath(String token) throws Status444UserIsNull;
}
