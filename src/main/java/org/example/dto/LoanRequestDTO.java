package org.example.dto;

import java.util.Date;

//simplified data structures used to transfer the data from the backend to the client,
// ensuring that only the necessary data is exposed.
public class LoanRequestDTO {
    private int id;
    private int userId;
    private String status;
    private Date created_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
