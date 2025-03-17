package org.example.model;

import java.util.Date;
//Map Loans to your database tables.
public class Loan {
    private int id;
    private int user_id;
    private double amount;
    private String loan_type;
    private String status;
    private Date created_date;

    public Loan() {

    }
    public Loan(int id, int user_id, double amount, String loan_type, Date created_date) {
        this.id = id;
        this.user_id = user_id;
        this.amount = amount;
        this.loan_type = loan_type;
        this.status = "PENDING";
        this.created_date = created_date;
    }
    public Loan(int id, int user_id, double amount, String loan_type, String status,Date created_date) {
        this.id = id;
        this.user_id = user_id;
        this.amount = amount;
        this.loan_type = loan_type;
        this.status = status;
        this.created_date = created_date;
    }

    //Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getLoan_type() {
        return loan_type;
    }

    public void setLoan_type(String loan_type) {
        this.loan_type = loan_type;
    }

    public String getStatus() {
        return status;
    }

    public void approved() { this.status="APPROVED";}
    public void rejected() { this.status="REJECTED";}

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }
}

