/*public class Transaction {
    private final String transactionID;
    private String userID;
    private String checkoutDate;
    private String dueData;
    private boolean checkout;
    public void Transaction(String transactionID, String userID, String checkoutDate, String dueData, boolean checkout) {
        this.transactionID = transactionID;
        this.userID = userID;
        this.checkoutDate = checkoutDate;
        this.dueData = dueData;
        this.checkout = checkout;

    }

    public boolean isCheckout() {
        return checkout;
    }

    public void setCheckout(boolean checkout) {
        this.checkout = checkout;
    }

    public String getDueData() {
        return dueData;
    }

    public void setDueData(String dueData) {
        this.dueData = dueData;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTransactionID() {
        return transactionID;
    }

    // toString method
    @Override
    public String toString() {
        String thickLine = "=========================================";
        String headerLine = "| %-15s | %-20s |";

        return String.format(
                "LibraryRecord:\n" +
                        thickLine + "\n" +
                        headerLine + "\n" +
                        thickLine + "\n" +
                        "| userID        : %-20s |\n" +
                        "| checkoutDate  : %-20s |\n" +
                        "| dueDate       : %-20s |\n" +
                        "| checkout      : %-20b |\n" +
                        thickLine,
                "Field", "Value",
                userID, checkoutDate, dueDate, checkout
        );
    }

}

 */