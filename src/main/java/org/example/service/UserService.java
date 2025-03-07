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
    // Fetch user info with access control
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }
    //Get all users
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

}
