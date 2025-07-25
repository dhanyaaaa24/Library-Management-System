package dao;

import model.Transaction;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    // Issue a book (create transaction and mark book unavailable)
    public void issueBook(int userId, int bookId) {
        String insertTransaction = "INSERT INTO transactions (user_id, book_id) VALUES (?, ?)";
        String updateBook = "UPDATE books SET available = false WHERE id = ?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt1 = conn.prepareStatement(insertTransaction);
                 PreparedStatement stmt2 = conn.prepareStatement(updateBook)) {

                stmt1.setInt(1, userId);
                stmt1.setInt(2, bookId);
                stmt1.executeUpdate();

                stmt2.setInt(1, bookId);
                stmt2.executeUpdate();

                conn.commit();
                System.out.println("✅ Book issued successfully!");

            } catch (SQLException e) {
                conn.rollback();
                System.out.println("❌ Failed to issue book: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("❌ Connection failed: " + e.getMessage());
        }
    }

    // Return a book (update return_date and make book available)
    public void returnBook(int transactionId) {
        String updateTransaction = "UPDATE transactions SET return_date = NOW() WHERE id = ?";
        String updateBook = "UPDATE books SET available = true WHERE id = (SELECT book_id FROM transactions WHERE id = ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt1 = conn.prepareStatement(updateTransaction);
                 PreparedStatement stmt2 = conn.prepareStatement(updateBook)) {

                stmt1.setInt(1, transactionId);
                stmt1.executeUpdate();

                stmt2.setInt(1, transactionId);
                stmt2.executeUpdate();

                conn.commit();
                System.out.println("✅ Book returned successfully!");

            } catch (SQLException e) {
                conn.rollback();
                System.out.println("❌ Failed to return book: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("❌ Connection failed: " + e.getMessage());
        }
    }

    // View all transactions
    public List<Transaction> getAllTransactions() {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Transaction t = new Transaction(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("book_id"),
                        rs.getTimestamp("issue_date"),
                        rs.getTimestamp("return_date")
                );
                list.add(t);
            }

        } catch (SQLException e) {
            System.out.println("❌ Failed to fetch transactions: " + e.getMessage());
        }

        return list;
    }
}
