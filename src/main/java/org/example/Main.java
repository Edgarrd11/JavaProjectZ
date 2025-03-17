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
        databaseManager.initDatabase();//Make this method persistant in the future, rename as initDatabase();
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
            app.post("/auth/register", authController::register);// Completed :) <- Needs to implement hashing
            app.post("/auth/login", authController::login);// Completed :)
            app.post("/auth/logout", authController::logout);// Completed :)

            // User Endpoints
            app.get("/users", userController::getAllUsers);// Completed :)
            app.get("/users/{id}", userController::getUserById);// Completed :)
            app.put("/users/{id}", userController::updateUserById);// Completed :) <- Needs to implement hashing (oly admin can every user)

            // Loans Endpoints
            app.get("/loans",loanController::getAllLoans);// Completed :) <- Needs to implement the user logic
            app.post("/loans", loanController::createLoan);// Completed :) <- Only users can create a loan
            app.get("/loans/{loanId}",loanController::getLoanById);// Completed :) <- Only users can create a loan
            app.put("/loans/{loanId}",loanController::updateLoanById);//Completed :) <- Only users can create a loan
            app.put("/loans/{loanId}/approve",loanController::approveLoan);//Completed :) <- Only Admin can approve
            app.put("/loans/{loanId}/reject",loanController::rejectLoan);//Completed :) <- Only Admin can reject

        /*
        *   Implements list:
        *   - Hashing
        *   - Login with Logback
        *   - Unite testing
        *   - Persistence in the database
        *
        * */
             System.out.println("Server running on http://localhost:7000/");
    }
}