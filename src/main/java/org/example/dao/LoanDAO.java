package org.example.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.example.model.Loan;
import org.example.model.User;
// Interact with the database, here we manage our CRUD operations and encapsuling SQL logic
public class LoanDAO {
    private final String url;
    public LoanDAO(String url) {
        this.url = url;
    }

    // Create
    public boolean createLoan(Loan loan) {
        String sql = "INSERT INTO loans (user_id, amount, loan_type) VALUES (?, ?, ?)";// Ajusta la query antes de probar
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, loan.getUser_id());
            stmt.setDouble(2, loan.getAmount());
            stmt.setString(3, loan.getLoan_type());

            int rowsInserted = stmt.executeUpdate();
            System.out.println("Loan created succesfuly!");
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // RETRIEVE a user by username (for login)
    public Loan getLoanById(int id) {
        String sql = "SELECT * FROM loans WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()){
                if (rs.next()) {
                    return new Loan(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getDouble("amount"),
                            rs.getString("loan_type"),
                            rs.getString("status"),
                            rs.getDate("created_date")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //RETRIEVE (all Users)
    public List<Loan> getAllLoans() {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM loans";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                loans.add(new Loan(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getDouble("amount"),
                        rs.getString("loan_type"),
                        rs.getString("status"),
                        rs.getDate("created_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }

    // UPDATE
    public void updateLoan(Loan loan)  {
        String sql = "UPDATE loans SET amount = ?, loan_type = ?, status = ? WHERE id = ?";
        System.out.println(loan.getStatus());
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Set the parameters for the update
            stmt.setDouble(1, loan.getAmount()); // amount
            stmt.setString(2, loan.getLoan_type()); // loan_type
            // status
            stmt.setString(3, loan.getStatus());
            stmt.setInt(4, loan.getId()); // id
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE a user by ID
    public void deleteLoan(int id) throws SQLException {
        String sql = "DELETE FROM loans WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
