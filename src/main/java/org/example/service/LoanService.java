package org.example.service;

import org.example.dao.LoanDAO;
import org.example.model.Loan;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.List;

public class LoanService {
    private final LoanDAO loanDAO;

    public LoanService(LoanDAO loanDAO ) {
        this.loanDAO = loanDAO;
    }
    //Register a new user
    public boolean createLoan(Loan loan) {
       return loanDAO.createLoan(loan);
    }

    public void updateLoan(Loan loan) { loanDAO.updateLoan(loan);}
    //Get all Loans
    public List<Loan> getAllLoans() {
        return loanDAO.getAllLoans();
    }
    // Get loan by ID
    public Loan getLoanById(int id) { return loanDAO.getLoanById(id);}


}
