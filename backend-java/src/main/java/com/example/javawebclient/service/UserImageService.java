package com.example.javawebclient.service;

import java.io.File;

import com.example.javawebclient.dto.user_image;
import com.example.javawebclient.repository.UserImageRepository;
import org.springframework.stereotype.Service;

@Service
public class UserImageService implements IUserImageService
{
    private final UserImageRepository repository = new UserImageRepository();

    public void uploadImage(user_image imageDTO)
    {
        repository.uploadImage(imageDTO);
    }
}
