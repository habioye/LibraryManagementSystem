package entity;

import java.util.List;

public class Book {
    private String bookId;
    private String bookTitle;
    private String description;
    private String author;
    private List<String> genre;
    private Boolean checkedOut;
    private String transactionId;

    // Book Constructor
    public Book(String bookId, String bookTitle, String author, String description, List<String> genre, Boolean checkedOut) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.description = description;
        this.author = author;
        this.genre = genre;
        this.checkedOut = checkedOut;
    }
    // getter and setter methods for the global variables

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

    public Boolean isCheckout() {
        return checkedOut;
    }

    public String getTransactionId() {
        return transactionId;
    }

    // Formats the book with a clean look
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔═══════════════════════════════════════════════════╗\n");
        sb.append(String.format("║ %-49s ║\n", "Title: " + bookTitle));
        sb.append("╠═══════════════════════════════════════════════════╣\n");
        sb.append(String.format("║ %-49s ║\n", "Author: " + author));
        sb.append("╠═══════════════════════════════════════════════════╣\n");
        sb.append("║ Description:                                      ║\n");
        sb.append(wrapText(description, 50));
        sb.append(String.format("║ %-49s ║\n", "Genre: " + genre));
        sb.append(String.format("║ %-49s ║\n", "CheckOut: " + checkedOut));
        sb.append("╚═══════════════════════════════════════════════════╝");
        return sb.toString();
    }
    // Formatter helper
    private String wrapText(String text, int maxLength) {
        StringBuilder result = new StringBuilder();
        int index = 0;
        while (index < text.length()) {
            int endIndex = Math.min(index + maxLength, text.length());
            if (endIndex < text.length() && !Character.isWhitespace(text.charAt(endIndex))) {
                // If the character at the end index is not whitespace, find the last whitespace character before it
                while (endIndex > index && !Character.isWhitespace(text.charAt(endIndex))) {
                    endIndex--;
                }
            }
            result.append("║ " + String.format("%-49s", text.substring(index, endIndex)) + " ║\n");
            index = endIndex;
        }
        return result.toString();
    }
}
