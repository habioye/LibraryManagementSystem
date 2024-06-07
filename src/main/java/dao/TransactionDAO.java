package dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import entity.Book;
import org.bson.Document;

import java.sql.Timestamp;
import java.util.ArrayList;

import entity.Transaction;
import org.bson.types.ObjectId;

public class TransactionDAO {

    static private MongoCollection<Document> collection;

    public static void initCollection(MongoCollection<Document> collect) {
        collection = collect;
    }

    // Create transaction record for a user checking out a book
    public static boolean addTransaction(String userId, String bookTitle) {

        ArrayList<Book> books = (ArrayList<Book>) BookDAO.getBookUsingFilter(1, bookTitle);
        if (books == null || books.size() == 0) {
            System.out.println("Book not found");
            return false;
        }

        // Get first book that's not checked out
        Book book = null;
        for (Book b : books) {
            if (!b.isCheckout()) {
                book = b;
                break;
            }
        }

        // TODO need to set book to checked out
        // Insert transaction
        Timestamp checkoutDate = new Timestamp(System.currentTimeMillis());
        Timestamp dueDate = new Timestamp(checkoutDate.getTime() + dayToMilliseconds(14));

        Document document = new Document()
                .append("userId", new ObjectId(userId))
                .append("bookId", new ObjectId(book.getBookId()))
                .append("checkoutDate", checkoutDate)
                .append("dueDate", dueDate)
                .append("checkedOut", true);

        if (collection.insertOne(document).wasAcknowledged())
            return true;
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
    public static ArrayList<Transaction> getTransactionsByUserId(String userId) {

        Document filter = new Document("userId", new ObjectId(userId));
        FindIterable<Document> result = collection.find(filter);

        ArrayList<Transaction> transactions = new ArrayList<>();
        // Check if operation was successful
        if (!result.iterator().hasNext())
            return transactions;

        for (Document d : result) {
            String transactionId = d.get("_id").toString();
            String bookId = d.get("bookId").toString();
            // TODO test if type cast works
            Timestamp checkoutDate = Timestamp.valueOf(d.get("checkoutDate").toString());
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