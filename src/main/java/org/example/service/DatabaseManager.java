package org.example.service;

import org.example.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

//Connects to PostgreSQL using JDBC
public class DatabaseManager {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/LoanMGMT?currentSchema=public&user=edgarrd11&password=admin";
    // 1) (Optional) DROP TABLES (to reset each time, ensuring a clean state for testing)
    private static final String DROP_TABLES_SQL = """
        DROP TABLE IF EXISTS loans CASCADE;
        DROP TABLE IF EXISTS users CASCADE;
        """;

    // 2) CREATE TABLES
    private static final String CREATE_TABLES_SQL = """
            CREATE TABLE users (
                id SERIAL PRIMARY KEY,
                name VARCHAR(50) NOT NULL,
                username VARCHAR(50) UNIQUE NOT NULL,
                password VARCHAR(255) NOT NULL,
                role VARCHAR(20) NOT NULL
            );    
            CREATE TABLE loans (
                id SERIAL PRIMARY KEY,
                user_id INT REFERENCES users(id),
                amount DECIMAL(10,2) NOT NULL,
                loan_type VARCHAR(50) NOT NULL,
                status VARCHAR(20),
                created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
        """;

    // 3) INSERT SAMPLE DATA
    private static final String INSERT_DATA_SQL = """
        INSERT INTO users (name, username, password, role)
            VALUES
            ('Alice Johnson', 'alicej', 'hashedpassword1', 'admin'),
            ('Bob Smith', 'bobsmith', 'hashedpassword2', 'user');
            
            INSERT INTO loans (user_id, amount, loan_type, status)
            VALUES
            (1, 5000.00, 'personal', 'PENDING'),
            (2, 12000.00, 'mortgage', 'PENDING');
        """;


    public void initDatabase() {
        runSql(DROP_TABLES_SQL, JDBC_URL);
        runSql(CREATE_TABLES_SQL, JDBC_URL);
        runSql(INSERT_DATA_SQL, JDBC_URL);
    }

    private static void runSql(String sql, String jdbcUrl) {
        try (Connection conn = DriverManager.getConnection(jdbcUrl);
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Executed SQL:\n" + sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean createUserInDB(User user) {
        // Query
        String sql = "INSERT INTO users (name, username, password, role) VALUES (?, ?, ?, ?)";
        // Hashing the password
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        // Sending the query to DB
        try (Connection conn = DriverManager.getConnection(JDBC_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, hashedPassword); // Use the hashed password
            stmt.setString(4, user.getRole());
            int rows = stmt.executeUpdate();
            System.out.println("User created succesfuly!");
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
            return false;
        }
    }

    public boolean userExists(String username) {
        String sql = "SELECT 1 FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();  // if there's a row, user exists
        } catch (SQLException e) {
            e.printStackTrace();
            return true; // fail safe, assume it exists if error
        }
    }

    public User getUserFromDB(String username) {
        // SQL query to fetch a user by username
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the username parameter in the query
            stmt.setString(1, username);

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            // Check if a user was found
            if (rs.next()) {
                // Create a new User object and populate its fields
                User user = new User();
                user.setId(rs.getInt("id")); // Maps to the "id" column
                user.setName(rs.getString("name")); // Maps to the "name" column
                user.setUsername(rs.getString("username")); // Maps to the "username" column
                user.setPassword(rs.getString("password")); // Maps to the "password" column
                user.setRole(rs.getString("role")); // Maps to the "role" column
                return user;
            }

            // If no user was found, return null
            return null;

        } catch (SQLException e) {
            // Log the error (use a proper logging framework in production)
            e.printStackTrace();
            return null;
        }
    }
}
