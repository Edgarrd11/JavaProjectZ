package org.example.service;

import org.mindrot.jbcrypt.BCrypt;
import org.example.dao.UserDAO;
import org.example.model.User;

import java.util.List;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO ) {
        this.userDAO = userDAO;
    }
    //Register a new user
    public boolean registerUser(String username, String password) {
        // Generate the hash
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
        if (userDAO.getUserByUsername(username) != null) {
            return false; // username exists
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        userDAO.createUser(newUser);

        return true;
    }

    //Login user
    public boolean loginUser(String username, String password) {
        User existingUser = userDAO.getUserByUsername(username);
        if (existingUser != null && BCrypt.checkpw(password, existingUser.getPassword())) {
            return true; // Valid credentials
        }
        return false;
    }
    //Get all users
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

}
