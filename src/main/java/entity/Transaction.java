package entity;

import dao.UserDAO;

import java.sql.Timestamp;

import static dao.UserDAO.getUserByID;

public class Transaction {
    private String transactionId;
    private String userId;
    private String bookId;
    private Timestamp checkoutDate;
    private Timestamp dueDate;
    private boolean checkedOut;

    public Transaction(String transactionId, String userId, String bookId, Timestamp checkoutDate, Timestamp dueDate, boolean checkedOut) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.bookId = bookId;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.checkedOut = checkedOut;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Timestamp getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Timestamp checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public Timestamp getDueDate() {
        return dueDate;
    }

    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    @Override
    public String toString() {

        User user = UserDAO.getUserByID(userId);
        String thickLine = "=========================================";
        String headerLine = "| %-15s | %-20s |";

        return String.format(
                "LibraryRecord:\n" +
                        thickLine + "\n" +
                        headerLine + "\n" +
                        thickLine + "\n" +
                        "| userID        : %-20s |\n" +
                        "| UserName        : %-20s |\n" +
                        "| FirstName        : %-20s |\n" +
                        "| LastName        : %-20s |\n" +
                        "| checkoutDate  : %-20s |\n" +
                        "| dueDate       : %-20s |\n" +
                        "| checkout      : %-20b |\n" +
                        thickLine,
                "Field", "Value",
                userId, user.getUsername(),user.getFirstName(), user.getLastName(),checkoutDate, dueDate, checkedOut);

    }

}

