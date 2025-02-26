package org.example.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Connects to PostgreSQL using JDBC
public class DatabaseManager {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/LoanMGMT?currentSchema=public&user=edgarrd11&password=admin";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL);
    }

    //var createTable = "CREATE TABLE users ( id SERIAL PRIMARY KEY, name VARCHAR(50) NOT NULL,username VARCHAR(50) UNIQUE NOT NULL, password VARCHAR(255) NOT NULL, role VARCHAR(20) NOT NULL); CREATE TABLE loans (id SERIAL PRIMARY KEY, user_id INT REFERENCES users(id), amount DECIMAL(10,2) NOT NULL, loan_type VARCHAR(50) NOT NULL, status VARCHAR(20) DEFAULT 'pending', created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP);";
}
