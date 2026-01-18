package com.example.javawebclient.repository;

import com.example.javawebclient.dto.UserDTO;
import com.example.javawebclient.dto.logginCredentialsDTO;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Repository;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import com.google.gson.Gson;

@Repository
public class UserRepository {

    private static final String API_URL = "http://127.0.0.1:8000/register";
    private static final String API_URL_LOGIN = "http://127.0.0.1:8000/login";
    private static final String API_URL_GET_ALL_USERS = "http://127.0.0.1:8000/allUsers";

    public void registerUser(UserDTO userDTO)
    {
        try {
            HttpClient client = HttpClient.newHttpClient();

            // Build the form-urlencoded body
            String body = "username=" + URLEncoder.encode(userDTO.username, StandardCharsets.UTF_8)
                    + "&usersurname=" + URLEncoder.encode(userDTO.usersurname, StandardCharsets.UTF_8)
                    + "&password=" + URLEncoder.encode(userDTO.password, StandardCharsets.UTF_8);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(API_URL))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("API response: " + response.body());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //log the user in (unique user based on username and password)
    public UserDTO loginUser(logginCredentialsDTO logginCredentials) {
        UserDTO user = null;
        try {
            HttpClient client = HttpClient.newHttpClient();

            String body = "username=" + URLEncoder.encode(logginCredentials.username, StandardCharsets.UTF_8)
                    + "&password=" + URLEncoder.encode(logginCredentials.password, StandardCharsets.UTF_8);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(API_URL_LOGIN))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("API Response after login: " + response.body());

            // Parse the JSON properly
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

            if (jsonObject.has("result") && !jsonObject.get("result").isJsonNull())
            {
                user = new Gson().fromJson(jsonObject.get("result"), UserDTO.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

//method to get all the users data
    public List<UserDTO> getAllUserData()
    {
        List<UserDTO> allUserDataList = new ArrayList<>();

        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(API_URL_GET_ALL_USERS))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();

            Type listType = new TypeToken<List<UserDTO>>() {}.getType();
            allUserDataList = gson.fromJson(response.body(), listType);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return allUserDataList;
    }
}
