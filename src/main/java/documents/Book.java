package documents;

import java.time.Instant;
import java.util.List;

public class Book {
    private String bookId;
    private String bookTitle;
    private String description;
    private String author;
    private List<String> genre;
    private boolean checkout;
    private String transactionId;

    public Book(String bookId, String bookTitle, String description, String author, List<String> genre, boolean checkout, String transactionId) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.description = description;
        this.author = author;
        this.genre = genre;
        this.checkout = checkout;
        this.transactionId = transactionId;
    }

    public String getBookId() {
        return bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public List<String> getGenre() {
        return genre;
    }

    public boolean isCheckout() {
        return checkout;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
