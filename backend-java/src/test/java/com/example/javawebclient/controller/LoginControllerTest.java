package com.example.javawebclient.controller;

import com.example.javawebclient.controller.UserController;
import com.example.javawebclient.dto.UserDTO;
import com.example.javawebclient.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testLoginSuccess() throws Exception {
        // Mock user returned from service
        UserDTO mockUser = new UserDTO();
        mockUser.username = "john";
        mockUser.usersurname = "doe";
        mockUser.token = "fake-jwt-token";

        Mockito.when(userService.loginUser(Mockito.any()))
                .thenReturn(mockUser);

        mockMvc.perform(post("/loginUser")
                        .param("username", "john")
                        .param("password", "1234"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-result"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void testLoginFailure() throws Exception {
        Mockito.when(userService.loginUser(Mockito.any()))
                .thenReturn(null);

        mockMvc.perform(post("/loginUser")
                        .param("username", "john")
                        .param("password", "wrong"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
}