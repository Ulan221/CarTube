package com.cartube.controller;


import com.cartube.entity.User;
import com.cartube.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public String addUser(@RequestBody final User user) {

        if (userService.userExists(user.getUsername())) {
            return "User with this username already exists";
        }
        userService.registerUser(user);
        return "User is saved";
    }


}
