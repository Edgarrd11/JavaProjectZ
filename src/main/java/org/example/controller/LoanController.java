package org.example.controller;
import io.javalin.http.Context;
import jakarta.servlet.http.HttpSession;
import org.example.model.Loan;
import org.example.model.User;
import org.example.service.LoanService;

import java.util.List;

public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    public void createLoan(Context ctx) {
        // Deserialize the JSON body of an HTTP into a Java Object
        Loan requestLoan = ctx.bodyAsClass(Loan.class);
        // Basic validation
        if(requestLoan.getUser_id() <= 0 ||  requestLoan.getAmount() <= 0 || requestLoan.getLoan_type() == null || requestLoan.getLoan_type().trim().isEmpty()) {
            ctx.status(400).json("{\"error\":\"Missing or invalid loan data\"}");
            return;
        }

        boolean created = loanService.createLoan(requestLoan);

        if(created) {
            ctx.status(201).json(requestLoan);
        } else {
            ctx.status(500).json("{\"error\":\"Failed to create a Loan\"}");
        }

    }


    public void getAllLoans(Context ctx) {
        List<Loan> loans = loanService.getAllLoans();
        ctx.json(loans);
    }

    public void getLoanById(Context ctx) {
        int loanId = Integer.parseInt(ctx.pathParam("loanId"));
        Loan loanInfoDb = loanService.getLoanById(loanId);
        HttpSession session = ctx.req().getSession();

        if (session != null && session.getAttribute("user") != null) {
            User userSession = (User) session.getAttribute("user");
            System.out.println("Usuario de la sesión actual: " + userSession.getUsername());
            System.out.println("Rol de la sesión actual: " + userSession.getRole());


            if (userSession.getRole().equals("admin")) {

                ctx.status(200).json(loanInfoDb);
            } else {
                //
                ctx.status(200).json(userSession);
            }

        } else {
            ctx.status(401).json("{\"error\":\"Not logged in\"}");
        }
    }

    public void updateLoanById(Context ctx) {
        int loanId = Integer.parseInt(ctx.pathParam("loanId"));
        Loan request = ctx.bodyAsClass(Loan.class);

        Loan loan = new Loan();
        loan.setId(loanId);
        loan.setUser_id(request.getUser_id());
        loan.setAmount(request.getAmount());
        loan.setLoan_type(request.getLoan_type());
        loan.setStatus(request.getStatus());
        loan.setCreated_date(request.getCreated_date());

        loanService.updateLoan(loan);
        ctx.status(200).json("{\"message\":\"Loan updated\"}");
    }
    public void approveLoan(Context ctx) {
        int loanId = Integer.parseInt(ctx.pathParam("loanId"));
        Loan loanInfoDb = loanService.getLoanById(loanId);

        if (loanInfoDb == null) {
            ctx.status(404).json("{\"error\":\"Loan not found\"}");
            return;
        }
        // Set the new status to APPROVED, keeping the other fields the same
        loanInfoDb.approved();
        // Call the service to update only the status
        loanService.updateLoan(loanInfoDb);
        // Return a success message along with the updated loan
        ctx.status(200).json(loanInfoDb);
    }
    public void rejectLoan(Context ctx) {
        int loanId = Integer.parseInt(ctx.pathParam("loanId"));
        Loan loanInfoDb = loanService.getLoanById(loanId);

        if (loanInfoDb == null) {
            ctx.status(404).json("{\"error\":\"Loan not found\"}");
            return;
        }

        // Set the new status to APPROVED, keeping the other fields the same
        loanInfoDb.rejected();
        // Call the service to update only the status
        loanService.updateLoan(loanInfoDb);
        // Return a success message along with the updated loan
        ctx.status(200).json(loanInfoDb);
    }
}




