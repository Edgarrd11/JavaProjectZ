package org.example.service;
import org.mindrot.jbcrypt.BCrypt;
import org.example.dao.UserDAO;
import org.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

// CRUD Actions
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO ) {
        this.userDAO = userDAO;
    }
    // Fetch user info with access control
    public User getUserById(int id) {
        logger.info("User get: " + id);
        return userDAO.getUserById(id);
    }
    //Get all users
    public List<User> getAllUsers() {
        logger.info("Get All Users");
        return userDAO.getAllUsers();
    }

    public void updateUser(User user) {
        logger.info("User updated " + user.getName());
        userDAO.updateUser(user);
    }

}
