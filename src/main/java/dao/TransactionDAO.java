package dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.sql.Timestamp;
import java.util.ArrayList;

import entity.Transaction;

public class TransactionDAO {

    static private MongoCollection<Document> collection;

    public static void initCollection(MongoCollection<Document> collect) {
        collection = collect;
    }

    // Create transaction record for a user checking out a book
    public static boolean addTransaction(String userId, String bookId) {

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        Document document = new Document()
                .append("userId", userId)
                .append("bookId", bookId)
                .append("checkoutDate", currentTimestamp)
                .append("dueDate", currentTimestamp.getTime() + dayToMilliseconds(14))  // Add x days
                .append("checkedOut", false);
        collection.insertOne(document);

        return false;
    }

    public static ArrayList<Transaction> getAllTransactions() {

        FindIterable<Document> result = collection.find();
        ArrayList<Transaction> transactions = new ArrayList<>();

        // Check if operation was successful
        if (!result.iterator().hasNext())
            return transactions;

        for (Document d : result) {
            String transactionId = d.get("_id").toString();
            String userId = d.get("userId").toString();
            String bookId = d.get("bookId").toString();
            // TODO test if type cast works
            Timestamp checkoutDate = (Timestamp) d.get("checkoutDate");
            Timestamp dueDate = (Timestamp) d.get("dueDate");
            boolean checkedOut = d.getBoolean("checkedOut");
            transactions.add(new Transaction(transactionId, userId, bookId, checkoutDate, dueDate, checkedOut));
        }

        return transactions;
    }

    // Get all transactions for given user
    public static ArrayList<Transaction> getTransactionsByUsername(String username) {

        Document filter = new Document("username", username);
        FindIterable<Document> result = collection.find(filter);

        ArrayList<Transaction> transactions = new ArrayList<>();
        // Check if operation was successful
        if (!result.iterator().hasNext())
            return transactions;

        for (Document d : result) {
            String transactionId = d.get("_id").toString();
            String userId = d.get("userId").toString();
            String bookId = d.get("bookId").toString();
            // TODO test if type cast works
            Timestamp checkoutDate = (Timestamp) d.get("checkoutDate");
            Timestamp dueDate = (Timestamp) d.get("dueDate");
            boolean checkedOut = d.getBoolean("checkedOut");
            transactions.add(new Transaction(transactionId, userId, bookId, checkoutDate, dueDate, checkedOut));
        }

        return transactions;
    }

    private static Long dayToMilliseconds(int days) {
        return Long.valueOf(days * 24 * 60 * 60 * 1000);
    }

}