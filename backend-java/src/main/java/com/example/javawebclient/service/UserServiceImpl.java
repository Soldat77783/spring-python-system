package com.example.javawebclient.service;

import com.example.javawebclient.dto.UserDTO;
import com.example.javawebclient.dto.logginCredentialsDTO;
import com.example.javawebclient.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository repository)
    {
        this.userRepository = repository;
    }

    @Override
    public void registerUser(UserDTO userDTO)
    {
        userRepository.registerUser(userDTO);
    }

    @Override
    public UserDTO loginUser(logginCredentialsDTO logginCredentials)
    {
        return userRepository.loginUser(logginCredentials);
    }

    @Override
    public List<UserDTO> getAllUserData()
    {
        return userRepository.getAllUserData();
    }
}
