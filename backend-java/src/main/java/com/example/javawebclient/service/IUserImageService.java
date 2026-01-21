package com.example.javawebclient.service;

import com.example.javawebclient.dto.user_image;
import com.example.javawebclient.dto.user_image_download_DTO;

import java.io.File;
import java.util.List;

public interface IUserImageService
{
    public void uploadImage(user_image imageDTO);
    public List<user_image_download_DTO> GetUserImages(int id);
}
