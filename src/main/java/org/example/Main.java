package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import io.javalin.Javalin;
import org.example.controller.LoanController;
import org.example.controller.UserController;
import org.example.controller.AuthController;
import org.example.dao.LoanDAO;
import org.example.dao.UserDAO;
import org.example.service.LoanService;
import org.example.service.UserService;
import org.example.service.DatabaseManager;

import java.sql.SQLException;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/LoanMGMT?currentSchema=public&user=edgarrd11&password=admin";
    public static void main(String[] args) {
        //Initialize DB
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.resetDatabase();
        //Create DAO, Services, Controllers
        UserDAO userDAO = new UserDAO(JDBC_URL);
        UserService userService = new UserService(userDAO);
        UserController userController = new UserController(userService);

        LoanDAO loanDAO = new LoanDAO(JDBC_URL);
        LoanService loanService = new LoanService(loanDAO);
        LoanController loanController = new LoanController(loanService);

        AuthController authController = new AuthController();
        //Start Javalin app
        Javalin app = Javalin.create(javalinConfig -> {
            // If needed, you can configure plugins, CORS, etc. here.
            // For example: config.plugins.enableCors(cors -> cors.add(anyOriginAllowed));
        } ).start(7000);
        // Auth Endpoints
        app.post("/auth/register", authController::register);
        app.post("/auth/login", authController::login);
        app.post("/auth/logout", authController::logout);

        // User Endpoints
        app.get("/users/{id}", userController::getUserById);
        app.put("/users/{id}", userController::updateUserById);

        // Loans Endpoints
        app.post("/loans", loanController::createLoan);
        app.get("/loans",loanController::getAllLoans);
        app.get("/loans/{loanId}",loanController::getLoanById);
        app.put("/loans/{loanId}",loanController::updateLoanById);
        app.put("/loans/{loanId}/approve",loanController::approveLoan);
        app.put("/loans/{loanId}/reject",loanController::rejectLoan);


        System.out.println("Server running on http://localhost:7000/");

    }
}