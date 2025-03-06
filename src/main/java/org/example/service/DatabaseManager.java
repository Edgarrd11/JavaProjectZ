package org.example.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
                status VARCHAR(20) DEFAULT 'pending',
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
            (1, 5000.00, 'personal', 'approved'),
            (2, 12000.00, 'mortgage', 'pending');
        """;


    public void resetDatabase() {
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

    //var createTable = "CREATE TABLE users ( id SERIAL PRIMARY KEY, name VARCHAR(50) NOT NULL,username VARCHAR(50) UNIQUE NOT NULL, password VARCHAR(255) NOT NULL, role VARCHAR(20) NOT NULL); CREATE TABLE loans (id SERIAL PRIMARY KEY, user_id INT REFERENCES users(id), amount DECIMAL(10,2) NOT NULL, loan_type VARCHAR(50) NOT NULL, status VARCHAR(20) DEFAULT 'pending', created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP);";
}
