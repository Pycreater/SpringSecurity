package com.security.service;

import com.security.entity.User;
import com.security.model.UserModel;

public interface UserService {
    User registerUser(UserModel userModel);

    void saveVerificationForUser(String token, User user);
}
