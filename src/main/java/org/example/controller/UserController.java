package org.example.controller;

import jakarta.servlet.http.HttpSession;
import org.example.dto.UserRequestDTO;
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
        int userId = Integer.parseInt(ctx.pathParam("id"));
        UserRequestDTO request = ctx.bodyAsClass(UserRequestDTO.class);
        // You can add the hashed here
        User user = new User();
        user.setId(userId);
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());

        userService.updateUser(user);
        ctx.status(200).json("{\"message\":\"User updated\"}");
    }

    // Handle GET /users/{id}
    public void getUserById(Context ctx) {
        System.out.println("Se ejecutó el GET /users/{id}");
        int userId = Integer.parseInt(ctx.pathParam("id"));
        System.out.println("ID de la petición: " + userId);

        User userInfoDb = userService.getUserById(userId);
        System.out.println("Usuario que se entregará: " + userInfoDb.getUsername());

        HttpSession session = ctx.req().getSession();

        if (session != null && session.getAttribute("user") != null) {
            User userSession = (User) session.getAttribute("user");
            System.out.println("Usuario de la sesión actual: " + userSession.getUsername());
            System.out.println("Rol de la sesión actual: " + userSession.getRole());

          
            if (userSession.getRole().equals("admin")) {
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
