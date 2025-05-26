package com.example.ecommerce.service;

import com.example.ecommerce.dto.LoginRequest;
import com.example.ecommerce.dto.Response;
import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.entity.User;

public interface UserService {
    Response registerUser(UserDto registrationRequest);
    Response loginUser(LoginRequest loginRequest);
    Response getAllUser();
    User getLoginUser();
    Response getUserInfoAndOrderHistory();
}
