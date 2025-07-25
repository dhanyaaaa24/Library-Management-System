package model;

import java.sql.Date;
import java.sql.Timestamp;

public class Transaction {
    private int id;
    private int bookId;
    private int userId;
    private Date issueDate;
    private Date returnDate;

    public Transaction(int id, int userId, int bookId, Timestamp issueDate, Timestamp returnDate) {}

    public Transaction(int id, int bookId, int userId, Date issueDate, Date returnDate) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public Date getIssueDate() { return issueDate; }
    public void setIssueDate(Date issueDate) { this.issueDate = issueDate; }

    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
}
