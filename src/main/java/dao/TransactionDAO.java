package dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import entity.Book;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entity.Transaction;
import org.bson.conversions.Bson;
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

        if(book == null)
            return false;

        // TODO need to set book to checked out
        BookDAO.checkOutBook(book.getBookId());
        // Insert transaction
        Timestamp checkoutDate = new Timestamp(System.currentTimeMillis());
        Timestamp dueDate = new Timestamp(checkoutDate.getTime() + dayToMilliseconds(14));

        Document document = new Document()
                .append("userId", new ObjectId(userId))
                .append("bookId", new ObjectId(book.getBookId()))
                .append("checkoutDate", checkoutDate)
                .append("dueDate", dueDate)
                .append("checkedOut", true);

        return collection.insertOne(document).wasAcknowledged();
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

            // Convert date to timestamp
            Date dateResult = (Date) d.get("checkoutDate");
            Timestamp checkoutDate = new Timestamp(dateResult.getTime());

            dateResult = (Date) d.get("dueDate");
            Timestamp dueDate = new Timestamp(dateResult.getTime());

            //Timestamp checkoutDate = Timestamp.valueOf(d.get("checkoutDate").toString());
            //Timestamp dueDate = (Timestamp) d.get("dueDate");

            dueDate.getTime();
            boolean checkedOut = d.getBoolean("checkedOut");
            transactions.add(new Transaction(transactionId, userId, bookId, checkoutDate, dueDate, checkedOut));
        }

        return transactions;
    }

    public static ArrayList<Book> getOverdueBooksByUserID(String userId) {

        Document filter = new Document("userId", new ObjectId(userId));
        FindIterable<Document> result = collection.find(filter);

        ArrayList<Book> overDueBooks = new ArrayList<>();
        result.forEach(doc -> {
            Date time = new Date();
            Date currentTime = new Date(time.getTime());
            Date dueDate = doc.getDate("dueDate");
            if(dueDate.before(currentTime)) {
                List<String> genres = doc.getList("genres", String.class);
                overDueBooks.add(new Book(doc.get("_id").toString(), doc.getString("title"),
                        doc.getString("description"), doc.getString("author"), genres,
                        doc.getBoolean("checkedOut"), doc.getString("currentTransactionId")));
            }
        });

        if(overDueBooks.isEmpty())
            return null;

        return overDueBooks;
    }

    private static Long dayToMilliseconds(int days) {
        return Long.valueOf(days * 24 * 60 * 60 * 1000);
    }

}