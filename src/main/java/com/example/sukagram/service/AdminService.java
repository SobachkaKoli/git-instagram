package com.example.sukagram.service;

import com.example.sukagram.Exception.Status430UserNotFoundException;
import com.example.sukagram.Exception.Status444UserIsNull;
import com.example.sukagram.model.User;

public interface AdminService {
    User banUserById(String userId) throws Status430UserNotFoundException, Status444UserIsNull;
    User unBanUserById(String userId) throws Status430UserNotFoundException, Status444UserIsNull;


}
