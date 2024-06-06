import com.mongodb.client.MongoCollection;
import database.DBConnection;
import doas.BookDAO;
import documents.Book;
import org.bson.Document;
import org.junit.Test;
import java.time.Instant;
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
        List<Book> books = BookDAO.getBookUsingFilter(3,"Genre1 Genre2");
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
        assertEquals(6,books.size());

    }


}
