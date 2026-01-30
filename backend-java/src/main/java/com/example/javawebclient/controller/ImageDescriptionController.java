package com.example.javawebclient.controller;

import com.example.javawebclient.dto.SetImageDescriptionDTO;
import com.example.javawebclient.dto.get_image_description_DTO;
import com.example.javawebclient.repository.ImageDescriptionRepository;
import com.example.javawebclient.service.IImageDescription;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ImageDescriptionController
{
    private final IImageDescription iImageDescription;

    @Autowired
    public ImageDescriptionController(IImageDescription imageDescription)
    {
        this.iImageDescription = imageDescription;
    }

    @GetMapping("/image-description")
    public String showDescriptionPage(@RequestParam("imageId") int imageId, Model model)
    {
        model.addAttribute("imageId", imageId);
        return "image_description_form";
    }

    @PostMapping("/setImageDescription")
    public String SetImageDescription(@RequestParam int id, @RequestParam String description, HttpSession session)
    {
        try
        {
            String token = (String) session.getAttribute("token");
            SetImageDescriptionDTO setImageDescriptionDTO = new SetImageDescriptionDTO();

            setImageDescriptionDTO.image_id = id;
            setImageDescriptionDTO.description = description;
            setImageDescriptionDTO.token = token;

            iImageDescription.SetImageDescription(setImageDescriptionDTO);

            System.out.println("SUCCESSFULLY ENTERED DESCRIPTION WITH BEARER TOKEN: " + token);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "login";
    }

    @GetMapping("/getImageDescription")
    public List<get_image_description_DTO> GetImageDescription(@RequestParam int id, HttpSession session)
    {
        List<get_image_description_DTO> results = new ArrayList<>();
        try
        {
            String token = (String) session.getAttribute("token");  //obtained from login controller

            if(token == null)
            {
                return results;
            }

            results = iImageDescription.GetImageDescriptions(id, token);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return results;
    }
}
