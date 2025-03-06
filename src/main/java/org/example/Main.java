package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import io.javalin.Javalin;
import org.example.controller.LoanController;
import org.example.controller.UserController;
import org.example.controller.AuthController;
import org.example.dao.UserDAO;
import org.example.service.UserService;

import java.sql.SQLException;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
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

    public static void main(String[] args) {
        //Initialize DB
        resetDatabase(JDBC_URL);
        //Create DAO, Services, Controllers
        UserDAO userDAO = new UserDAO(JDBC_URL);
        UserService userService = new UserService(userDAO);
        UserController userController = new UserController(userService);
        AuthController authController = new AuthController();
        LoanController loanController = new LoanController();

        //Start Javalin app
        Javalin app = Javalin.create(javalinConfig -> {
            // If needed, you can configure plugins, CORS, etc. here.
            // For example: config.plugins.enableCors(cors -> cors.add(anyOriginAllowed));
        } ).start(7000);
        //Define routes
        app.post("/auth/register", authController::register);
        app.post("/auth/login", authController::login);
        app.post("/auth/logout", authController::logout);

        app.get("/users/{id}", userController::getUserById);
        app.put("/users/{id}", userController::updateUserById);

        app.post("/loans", loanController::createLoan);
        app.get("/loans",loanController::getAllLoans);
        app.get("/loans/{loanId}",loanController::getLoanById);
        app.put("/loans/{loanId}",loanController::updateLoanById);
        app.put("/loans/{loanId}/approve",loanController::approveLoan);
        app.put("/loans/{loanId}/reject",loanController::rejectLoan);


        System.out.println("Server running on http://localhost:7000/");

    }
    private static void resetDatabase(String jdbcUrl) {
        runSql(DROP_TABLES_SQL, jdbcUrl);
        runSql(CREATE_TABLES_SQL, jdbcUrl);
        runSql(INSERT_DATA_SQL, jdbcUrl);
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
}