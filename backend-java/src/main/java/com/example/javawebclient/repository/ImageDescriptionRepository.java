package com.example.javawebclient.repository;

import com.example.javawebclient.dto.SetImageDescriptionDTO;
import com.example.javawebclient.dto.UserDTO;
import com.example.javawebclient.dto.get_image_description_DTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ImageDescriptionRepository
{
    private static String SET_IMAGE_DESCRIPTION = "http://127.0.0.1:8000/upload_image_description";
    private static String GET_IMAGE_DESCRIPTION = "http://127.0.0.1:8000/Get_Image_Description";

    //method to set the image description
    public void SetImageDescription(SetImageDescriptionDTO setImageDescriptionDTO)
    {
        try {
            HttpClient client = HttpClient.newHttpClient();

            String body = "id=" + URLEncoder.encode(String.valueOf(setImageDescriptionDTO.image_id), StandardCharsets.UTF_8)
                    + "&description=" + URLEncoder.encode(setImageDescriptionDTO.description, StandardCharsets.UTF_8);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(SET_IMAGE_DESCRIPTION))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Authorization", "Bearer " + setImageDescriptionDTO.token)
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Uploaded description successfully: " + response.body());
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }

    //method to get the descriptions for each image
    public List<get_image_description_DTO> GetImageDescriptions(int id, String token)
    {
        List<get_image_description_DTO> imageDescriptions = new ArrayList<>();
        String URL = GET_IMAGE_DESCRIPTION + "?id=" + id;

        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URL))
                    .header("Authorization", "Bearer " + token)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("API RESPONSE from response: " + response.body());

            // ⭐ IMPORTANT: Use GsonBuilder so LocalDateTime can be parsed
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>)
                            (json, type, context) -> LocalDateTime.parse(json.getAsString()))
                    .create();

            Type listType = new TypeToken<List<get_image_description_DTO>>() {}.getType();
            imageDescriptions = gson.fromJson(response.body(), listType);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return imageDescriptions;
    }
}
