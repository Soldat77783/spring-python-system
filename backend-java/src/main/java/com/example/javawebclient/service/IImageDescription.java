package com.example.javawebclient.service;

import com.example.javawebclient.dto.SetImageDescriptionDTO;
import com.example.javawebclient.dto.get_image_description_DTO;
import com.example.javawebclient.repository.ImageDescriptionRepository;

import java.util.List;

public interface IImageDescription
{
    public void SetImageDescription(SetImageDescriptionDTO setImageDescriptionDTO);
    public List<get_image_description_DTO> GetImageDescriptions(int id, String token);
}
