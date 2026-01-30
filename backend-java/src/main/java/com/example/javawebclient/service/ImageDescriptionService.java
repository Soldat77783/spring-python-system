package com.example.javawebclient.service;

import com.example.javawebclient.controller.ImageDescriptionController;
import com.example.javawebclient.dto.SetImageDescriptionDTO;
import com.example.javawebclient.dto.get_image_description_DTO;
import com.example.javawebclient.repository.ImageDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageDescriptionService implements IImageDescription
{
    private final ImageDescriptionRepository imageDescriptionRepository;

    @Autowired
    public ImageDescriptionService(ImageDescriptionRepository imageDescriptionRepository)
    {
        this.imageDescriptionRepository = imageDescriptionRepository;
    }

    @Override
    public void SetImageDescription(SetImageDescriptionDTO setImageDescriptionDTO)
    {
        imageDescriptionRepository.SetImageDescription(setImageDescriptionDTO);
    }

    @Override
    public List<get_image_description_DTO> GetImageDescriptions(int id, String token)
    {
        return imageDescriptionRepository.GetImageDescriptions(id, token);
    }
}

