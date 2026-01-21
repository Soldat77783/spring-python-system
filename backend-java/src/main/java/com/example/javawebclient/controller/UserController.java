package com.example.javawebclient.controller;

import com.example.javawebclient.dto.UserDTO;
import com.example.javawebclient.dto.logginCredentialsDTO;
import com.example.javawebclient.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String showRegisterPage() {
        return "register"; // register.html in templates
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String usersurname, @RequestParam String password) {
        UserDTO userDTO = new UserDTO();
        userDTO.username = username;
        userDTO.usersurname = usersurname;
        userDTO.password = password;

        userService.registerUser(userDTO);
        return "redirect:/"; // redirect or you can show a success page
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // login.html in templates
    }

    @PostMapping("/loginUser")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model, HttpSession session)
    {
        logginCredentialsDTO loginCredentials = new logginCredentialsDTO();
        loginCredentials.username = username;
        loginCredentials.password = password;

        UserDTO user = userService.loginUser(loginCredentials);

        if (user == null) {
            return "login"; // could add a message for invalid login
        }

        session.setAttribute("loggedInUser", user); //here i am storing the user details so i can use the id across pages

        model.addAttribute("user", user);  //this is a key-value pair user user
        return "user-result"; // user-result.html to display data
    }

    @GetMapping("/users")
    public String showAllUsers(Model model) {
        List<UserDTO> users = userService.getAllUserData();
        model.addAttribute("users", users);
        return "allUsers";
    }

}
