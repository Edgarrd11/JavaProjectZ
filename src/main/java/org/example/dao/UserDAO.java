package org.example.dao;

import org.example.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Interact with the database, here we manage our CRUD operations and encapsuling SQL logic
public class UserDAO {
    private final String url;

    public UserDAO(String url) {
        this.url = url;
    }
    //CREATE
    public User createUser(User newUser) {
        String sql_query = "INSERT INTO users (username, password_hash) VALUES (?, ?)";
        try(Connection conn = DriverManager.getConnection(url);
        PreparedStatement statement = conn.prepareStatement(sql_query)) {
            statement.setString(1, newUser.getUsername());
            statement.setString(2, newUser.getPassword());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newUser;
    }
    // RETRIEVE a user by username (for login)
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Parameter Binding:
            stmt.setString(1, username);
            // This binds the username parameter to the ? in the SQL query (preventing SQL injection)
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(

                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //RETRIEVE (all Users)
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // UPDATE a user's profile
    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET username = ?, password = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url);
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword()); // Password should be hashed
            stmt.setInt(3, user.getId());
            stmt.executeUpdate();
        }
    }

    // DELETE a user by ID
    public void deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
