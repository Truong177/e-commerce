package com.example.ecommerce.service.impl;

import com.example.ecommerce.config.JwtUtils;
import com.example.ecommerce.dto.LoginRequest;
import com.example.ecommerce.dto.Response;
import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.entity.emum.UserRole;
import com.example.ecommerce.exception.InvalidCredentialsException;
import com.example.ecommerce.mapper.EntityDtoMapper;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.hibernate.query.sqm.tree.SqmNode.log;
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtUtils jwtUtils;
    private EntityDtoMapper entityDtoMapper;


    @Override
    public Response registerUser(UserDto registrationRequest) {
        UserRole role = UserRole.USER;
        if (registrationRequest.getRole() != null && registrationRequest.equals(UserRole.ADMIN)) {
            role = UserRole.ADMIN;
        }
        User user = User.builder()
                .name(registrationRequest.getName())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .password(registrationRequest.getPassword())
                .role(role)
                .build();
        User savedUser = userRepository.save(user);
        UserDto userDto = entityDtoMapper.mapUserToDtoBasic(savedUser);
        return Response.builder()
                .status(200)
                .message("User registered successfully")
                .user(userDto)
                .build();
    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: "));
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Password does not match");
        }
        String token = jwtUtils.generateToken(user);
        return Response.builder()
                .status(200)
                .message("User successfully logged in")
                .token(token)
                .expirationTime("1 day")
                .role(user.getRole().name())
                .build();
    }

    @Override
    public Response getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream()
                .map(entityDtoMapper::mapUserToDtoBasic)
                .toList();
        return Response.builder()
                .status(200)
                .message("successfully")
                .userList(userDtos)
                .build();
    }

    @Override
    public User getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        log.info("User Email is: " +email);
        return userRepository.findByEmail(email).
                orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    @Override
    public Response getUserInfoAndOrderHistory() {
        User user = getLoginUser();
        UserDto userDto = entityDtoMapper.mapUserToDtoPlusAddressAndOrderItems(user);
        return Response.builder()
                .status(200)
                .user(userDto)
                .build();
    }
}
