package com.example.javawebclient.service;

import com.example.javawebclient.dto.UserDTO;
import com.example.javawebclient.dto.logginCredentialsDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserService
{
    public void registerUser(UserDTO userDTO);
    public UserDTO loginUser(logginCredentialsDTO logginCredentials);
    public List<UserDTO> getAllUserData();
}
