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
    public Loan createLoan(Loan loan) {
        String sql_query = "INSERT INTO loans (username, password_hash) VALUES (?, ?)";// Ajusta la query antes de probar
        return loan;
    }
    // RETRIEVE a user by username (for login)
    public Loan getLoanById(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";// Ajusta la query
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Parameter Binding:
            stmt.setString(1, username);
            // This binds the username parameter to the ? in the SQL query (preventing SQL injection)
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Loan();// Add params
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
                loans.add(new Loan());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }

    // UPDATE a user's profile
    public void updateLoan(Loan loan) throws SQLException {
        String sql = "UPDATE users SET username = ?, password = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            //stmt.setString(1, loan.getId());// Add requirements
            stmt.executeUpdate();
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
