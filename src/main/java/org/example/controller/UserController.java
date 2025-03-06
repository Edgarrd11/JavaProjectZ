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
    public void register(Context ctx) {
        // Parse request JSON into our DTO
        UserAuthDTO req = ctx.bodyAsClass(UserAuthDTO.class);

        if (req.getUsername() == null || req.getPassword() == null) {
            ctx.status(400).json("{\"error\":\"Missing username or password\"}");
            return;
        }
        boolean success = userService.registerUser(req.getUsername(), req.getPassword());
        if (success) {
            ctx.status(201).json("{\"message\":\"User registered successfully\"}");
        } else {
            ctx.status(409).json("{\"error\":\"Username already exists\"}");
        }
    }
    public void login(Context ctx) {
        UserAuthDTO user = ctx.bodyAsClass(UserAuthDTO.class);

        if (user.getUsername() == null || user.getPassword() == null) {
            ctx.status(400).json("{\"error\":\"Missing username or password\"}");
            return;
        }

        boolean success = userService.loginUser(user.getUsername(), user.getPassword());
        if (success) {
            // Typically you might return a JWT or session token here
            // For now we will pretend they are authenticated without cookies

            ctx.status(200).json("{\"message\":\"Login successful\"}");
        } else {
            ctx.status(401).json("{\"error\":\"Invalid credentials\"}");
        }
    }

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
