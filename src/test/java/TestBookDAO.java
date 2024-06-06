import com.mongodb.client.MongoCollection;
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
    public void testGetBooks(){
        BookDAO bookDAO = new BookDAO("BookTest");
        List<Book> books = bookDAO.getBookUsingFilter(1,"Title1");
        for (Book book: books){
            System.out.println(book);
        }
        System.out.println("___________________________");
        books = bookDAO.getBookUsingFilter(2,"Author1");
        for (Book book: books){
            System.out.println(book);
        }

        System.out.println("___________________________");
        books = bookDAO.getBookUsingFilter(3,"Genre2 Genre1");
        for (Book book: books){
            System.out.println(book);
        }
    }
}
