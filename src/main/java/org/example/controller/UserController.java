package org.example.controller;

import org.example.dto.UserAuthDTO;
import org.example.model.User;
import org.example.service.UserService;

import io.javalin.http.Context;

import java.util.List;

// Calls services and deals with HTTP Request
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    //Register a user
    /**
     * GET /users
     * Returns JSON of all users
     */
    public void updateUserById(Context ctx) {

    }

    public void getUserById(Context ctx) {

    }
    public void getAllUsers(Context ctx) {
        List<User> users = userService.getAllUsers();
        ctx.json(users);
    }




}
