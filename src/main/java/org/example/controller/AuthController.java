package org.example.controller;
import io.javalin.http.Context;
import jakarta.servlet.http.HttpSession;
import org.example.model.User;
import org.example.service.DatabaseManager;
import org.mindrot.jbcrypt.BCrypt;

// Manages Auths across the app
public class AuthController {
    DatabaseManager databaseManager = new DatabaseManager();

    // Register a new user
    public void register(Context ctx) {
        // Deserialize the JSON body of an HTTP request into a Java object.
        User requestUser = ctx.bodyAsClass(User.class);
        // Basic validation
        if (requestUser.getUsername() == null || requestUser.getPassword() == null) {
            ctx.status(400).json("{\"error\":\"Missing username or password\"}");
            return;
        }
        // Check if user already exists
        if (databaseManager.userExists(requestUser.getUsername())) {
            ctx.status(409).json("{\"error\":\"Username already taken\"}");
            return;
        }
        // Insert new user
        boolean created = databaseManager.createUserInDB(requestUser);
        if (created) {
            ctx.status(201).json(requestUser);
        } else {
            ctx.status(500).json("{\"error\":\"Failed to register user\"}");
        }
    }

    // Login
    public void login(Context ctx) {
        // Step 1: Parse the request body into a User object
        User requestUser = ctx.bodyAsClass(User.class);
        // Step 2: Validate the request
        if (requestUser.getUsername() == null || requestUser.getPassword() == null) {
            ctx.status(400).json("{\"error\":\"Missing username or password\"}");
            return;
        }
        // Step 3: Fetch the user from the database
        User dbUser = databaseManager.getUserFromDB(requestUser.getUsername());
        // Step 4: Check if the user exists in the database
        if (dbUser == null) {
            ctx.status(401).json("{\"error\":\"Invalid credentials\"}");
            return;
        }
        // Step 5: Verify the password using BCrypt
        // Retrieve the hashed password from the database and compare it with the plain-text password
        boolean isPasswordValid = BCrypt.checkpw(requestUser.getPassword(), dbUser.getPassword());
        // Step 6: Handle password verification result
        if (!isPasswordValid) {
            ctx.status(401).json("{\"error\":\"Invalid credentials\"}");
            return;
        }
        // Step 7: Create a session for the authenticated user
        HttpSession session = ctx.req().getSession(true);
        session.setAttribute("user", dbUser);
        // Step 8: Return a success response
        System.out.println("Login succesfully: " + dbUser.getName());
        ctx.status(200).json(dbUser);
    }

    // Logout
    public void logout(Context ctx) {
        HttpSession session = ctx.req().getSession();
        if (session != null) {
            session.invalidate();
        }
        System.out.println("Logout succesfuly");
        ctx.status(200).json("{\"message\":\"Logged out\"}");
    }

    public void checkLogin(Context ctx) {

    }




}
