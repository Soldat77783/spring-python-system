package com.example.javawebclient.controller;

import com.example.javawebclient.dto.UserDTO;
import com.example.javawebclient.dto.user_image;
import com.example.javawebclient.dto.user_image_download_DTO;
import com.example.javawebclient.service.IUserImageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class UserImageController
{
    private final IUserImageService iUserImageService;

    @Autowired
    public UserImageController(IUserImageService iuserImageService)
    {
        this.iUserImageService = iuserImageService;
    }

    @PostMapping("/uploadImage")
    public String uploadImage(@RequestParam("userId") int userId,
                              @RequestParam("imageFile") MultipartFile file) {
        try {
            user_image imageDTO = new user_image();
            imageDTO.user_id = userId;
            imageDTO.image_name = file.getOriginalFilename();
            imageDTO.image_type = file.getContentType();
            imageDTO.image_data = file.getBytes(); // IOException handled here
            imageDTO.uploaded_at = new Date();

            iUserImageService.uploadImage(imageDTO);

        } catch (IOException e) {
            e.printStackTrace();
            return "error"; // optional: a page to show the error
        }

        return "redirect:/";
    }

    @GetMapping("/user-images")
    public String GetUserImages(HttpSession session, Model model)
    {
        UserDTO user = (UserDTO) session.getAttribute("loggedInUser");

        if(user == null)
        {
            return "redirect:/login";
        }

        int userId = user.id;

        List<user_image_download_DTO> images = iUserImageService.GetUserImages(userId);

        model.addAttribute("userId", userId);
        model.addAttribute("images", images);

        return "user_images";
    }


}
