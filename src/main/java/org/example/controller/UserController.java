package org.example.controller;

import jakarta.servlet.http.HttpSession;
import org.example.dto.UserAuthDTO;
import org.example.model.User;
import org.example.service.UserService;
import io.javalin.http.HttpStatus;
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

    // Handle GET /users/{id}
    public void getUserById(Context ctx) {
        System.out.println("Se ejecuto el get /users/{id}");
        int userId = Integer.parseInt(ctx.pathParam("id"));
        System.out.println("ID de la petición"+userId);
        User userInfoDb = userService.getUserById(userId);
        System.out.println("Ususario que se entregara: " + userInfoDb.getUsername());
        HttpSession session = ctx.req().getSession();
        // Extract user info for current session
        if (session != null && session.getAttribute("user") != null) {
            User userSession = (User) session.getAttribute("user");
            System.out.println("Usuario de la sesion actual: " + userSession);
            //Check User role
            if(userSession.getRole() == "admin") {
                //Retrive user ID info
                ctx.status(200).json(userInfoDb);
            } else {
                ctx.status(200).json(userSession);
            }


        } else {
            ctx.status(401).json("{\"error\":\"Not logged in\"}");
        }
    }
    public void getAllUsers(Context ctx) {
        List<User> users = userService.getAllUsers();
        ctx.json(users);
    }




}
