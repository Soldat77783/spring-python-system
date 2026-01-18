package com.example.javawebclient.repository;

import com.example.javawebclient.dto.user_image;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.file.Files;
import java.net.http.HttpResponse;
import java.util.UUID;

public class UserImageRepository {

    private static final String UPLOAD_IMAGE_API_URL = "http://127.0.0.1:8000/upload-image";

    public void uploadImage(user_image imageDTO) {
        try {
            String boundary = UUID.randomUUID().toString();

            // Use the bytes already in the DTO
            byte[] fileBytes = imageDTO.image_data;

            // Build multipart body manually
            String body =
                    "--" + boundary + "\r\n" +
                            "Content-Disposition: form-data; name=\"user_id\"\r\n\r\n" +
                            imageDTO.user_id + "\r\n" +
                            "--" + boundary + "\r\n" +
                            "Content-Disposition: form-data; name=\"image\"; filename=\"" + imageDTO.image_name + "\"\r\n" +
                            "Content-Type: " + imageDTO.image_type + "\r\n\r\n";

            byte[] bodyBytes = body.getBytes();
            byte[] endBytes = ("\r\n--" + boundary + "--").getBytes();

            byte[] finalBody = new byte[bodyBytes.length + fileBytes.length + endBytes.length];
            System.arraycopy(bodyBytes, 0, finalBody, 0, bodyBytes.length);
            System.arraycopy(fileBytes, 0, finalBody, bodyBytes.length, fileBytes.length);
            System.arraycopy(endBytes, 0, finalBody, bodyBytes.length + fileBytes.length, endBytes.length);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(UPLOAD_IMAGE_API_URL))
                    .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                    .POST(HttpRequest.BodyPublishers.ofByteArray(finalBody))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Image upload response: " + response.body());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
