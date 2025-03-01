package org.example.service;

import org.example.dao.LoanDAO;
import org.example.model.Loan;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class LoanService {
    private final LoanDAO loanDAO;

    public LoanService(LoanDAO loanDAO ) {
        this.loanDAO = loanDAO;
    }
    //Register a new user
    public boolean registerLoan(String username, String password) {
        // Generate the hash
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
        if (loanDAO.getLoanById(username) != null) {
            return false; // username exists
        }
        Loan newLoan = new Loan();
        //newLoan.setUsername(username);
        //newLoan.setPassword(password);
        //userLoan.createLoan(newLoan);

        return true;
    }

    //Get all Loans
    public List<Loan> getAllLoans() {
        return loanDAO.getAllLoans();
    }


}
