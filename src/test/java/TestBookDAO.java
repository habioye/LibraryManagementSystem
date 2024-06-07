import connection.DBConnection;
import dao.BookDAO;
import dao.UserDAO;
import entity.Book;
import entity.User;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
public class TestBookDAO {
    @Test
    public void testGetBooksFilterByTitle(){
        DBConnection dbConnection = new DBConnection();
        dbConnection.getCollection("BookTest").deleteMany(new Document());
        dbConnection.getCollection("UserTest").deleteMany(new Document());
        SetUpTest.setUp();
        List<Book> books = BookDAO.getBookUsingFilter(1,"title1");
        assertEquals(2,books.size());
        assertEquals("Title1",books.get(0).getBookTitle());
        assertEquals("Title1",books.get(1).getBookTitle());
    }

    @Test
    public void testGetBooksFilterByAUthor(){
        DBConnection dbConnection = new DBConnection();
        dbConnection.getCollection("BookTest").deleteMany(new Document());
        dbConnection.getCollection("UserTest").deleteMany(new Document());
        SetUpTest.setUp();
        List<Book> books = BookDAO.getBookUsingFilter(2,"Author1");
        assertEquals(4,books.size());
        assertEquals("Author1",books.get(0).getAuthor());
        assertEquals("Author1",books.get(1).getAuthor());
        assertEquals("Author1",books.get(2).getAuthor());
        assertEquals("Author1",books.get(3).getAuthor());

    }
    @Test
    public void testGetBooksFilterByOneGenre(){
        DBConnection dbConnection = new DBConnection();
        dbConnection.getCollection("BookTest").deleteMany(new Document());
        dbConnection.getCollection("UserTest").deleteMany(new Document());
        SetUpTest.setUp();
        List<Book> books = BookDAO.getBookUsingFilter(3,"Genre3");
        assertEquals(2,books.size());
        assertEquals("Title2",books.get(0).getBookTitle());
        assertEquals("Title4",books.get(1).getBookTitle());
    }
    @Test
    public void testGetBooksFilterByManyGenre(){
        DBConnection dbConnection = new DBConnection();
        dbConnection.getCollection("BookTest").deleteMany(new Document());
        dbConnection.getCollection("UserTest").deleteMany(new Document());
        SetUpTest.setUp();
        List<Book> books = BookDAO.getBookUsingFilter(3,"Genre1 genre2");
        assertEquals(2,books.size());
        assertEquals("Title1",books.get(0).getBookTitle());
        assertEquals("Title1",books.get(1).getBookTitle());
    }
    @Test
    public void testGetBooks(){
        DBConnection dbConnection = new DBConnection();
        dbConnection.getCollection("BookTest").deleteMany(new Document());
        dbConnection.getCollection("UserTest").deleteMany(new Document());
        SetUpTest.setUp();
        List<Book> books = BookDAO.getBooks();
        assertEquals(9,books.size());

    }
    @Test
    public void test_delete_book_with_valid_id() {
        DBConnection dbConnection = new DBConnection();
        dbConnection.getCollection("BookTest").deleteMany(new Document());
        BookDAO.BookDAOInit(dbConnection.getCollection("BookTest"));

        // Add a book to delete
        BookDAO.addBook("Title1", "Author1", "Description1", Arrays.asList("Genre1", "Genre2"));
        List<Book> books = BookDAO.getBooks();
        assertEquals(1, books.size());

        // Delete the book
        String bookId = books.get(0).getBookId();
        BookDAO.deleteBook(bookId);

        // Verify the book is deleted
        books = BookDAO.getBooks();
        assertEquals(0, books.size());
    }
    @Test
    public void test_delete_book_with_non_existent_id() {
        DBConnection dbConnection = new DBConnection();
        dbConnection.getCollection("BookTest").deleteMany(new Document());
        BookDAO.BookDAOInit(dbConnection.getCollection("BookTest"));

        // Add a book to ensure collection is not empty
        BookDAO.addBook("Title1", "Author1", "Description1", Arrays.asList("Genre1", "Genre2"));
        List<Book> books = BookDAO.getBooks();
        assertEquals(1, books.size());

        // Attempt to delete a non-existent book
        String nonExistentId = new ObjectId().toHexString();
        BookDAO.deleteBook(nonExistentId);

        // Verify the book is still there
        books = BookDAO.getBooks();
        assertEquals(1, books.size());
    }



    // Returns a list of books that are currently checked out
    @Test
    public void test_returns_checked_out_books() {
        DBConnection dbConnection = new DBConnection();
        dbConnection.getCollection("BookTest").deleteMany(new Document());
        BookDAO.BookDAOInit(dbConnection.getCollection("BookTest"));
        UserDAO.initializeCollections(dbConnection.getCollection("UserTest"),
                dbConnection.getCollection("BookTest"));

        List<String> genres = new ArrayList<>();
        genres.add("Fiction");
        BookDAO.addBook("Title1", "Author1", "Description1", genres);
        BookDAO.addBook("Title2", "Author2", "Description2", genres);


        List<Book> books = BookDAO.viewAllCheckedOutBook();
        assertEquals(0, books.size());

        List<Book> allBooks = BookDAO.getBooks();
        BookDAO.checkOutBook(allBooks.get(0).getBookId());

        books = BookDAO.viewAllCheckedOutBook();
        assertEquals(1, books.size());
        assertEquals("Title1", books.get(0).getBookTitle());
    }

    // Collection is not initialized, should return null
    @Test
    public void test_collection_not_initialized() {
        BookDAO.BookDAOInit(null);
        List<Book> books = BookDAO.viewAllCheckedOutBook();
        assertNull(books);
    }

    // Document fields are missing or null, should handle without throwing exceptions
    @Test
    public void test_document_fields_missing_or_null_handling() {
        // Setup
        DBConnection dbConnection = new DBConnection();
        dbConnection.getCollection("BookTest").deleteMany(new Document());
        dbConnection.getCollection("UserTest").deleteMany(new Document());
        BookDAO.BookDAOInit(dbConnection.getCollection("BookTest"));
        SetUpTest.setUp();

        // Test
        List<Book> checkedOutBooks = BookDAO.viewAllCheckedOutBook();

        // Assertions
        assertNotNull(checkedOutBooks);
        // Add more assertions as needed to test the behaviour
    }

    @Test
    public void test_collection_contains_documents_not_matching_schema() {
        // Setup - insert documents with incorrect schema into the collection
        DBConnection dbConnection = new DBConnection();
        BookDAO.BookDAOInit(dbConnection.getCollection("BookTest"));
        dbConnection.getCollection("BookTest").deleteMany(new Document());
        dbConnection.getCollection("BookTest").insertOne(new Document("title", "Title1"));
        dbConnection.getCollection("BookTest").insertOne(new Document("author", "Author1"));

        // Test the method viewAllCheckedOutBook
        List<Book> checkedOutBooks = BookDAO.getBooks();

        // Assertion - checkedOutBooks should be empty as documents do not match the expected schema
        assertEquals(0, checkedOutBooks.size());
    }
}
