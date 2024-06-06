import com.mongodb.client.MongoCollection;
import database.DBConnection;
import doas.BookDAO;
import documents.Book;
import org.bson.Document;

import java.util.Arrays;

public class SetUpTest {
    // CALL THIS ONCE
    public static void main(String[] args) {
        BookDAO bookDAO = new BookDAO("BookTest");
        bookDAO.addBook("Title1","Author1","Des1", Arrays.asList("Genre1","Genre2"));
        bookDAO.addBook("Title1","Author1","Des1", Arrays.asList("Genre1","Genre2"));
        bookDAO.addBook("Title2","Author2","Des2", Arrays.asList("Genre3","Genre2"));
        bookDAO.addBook("Title3","Author1","Des3", Arrays.asList("Genre1","Genre4"));
        bookDAO.addBook("Title4","Author1","Des4", Arrays.asList("Genre5","Genre3"));
        bookDAO.addBook("Title5","Author3","Des5", Arrays.asList("Genre6","Genre7"));

        DBConnection db = new DBConnection();
        MongoCollection<Document> collection = db.getCollection("UserTest");
        collection.insertOne(new Document("username", "user1")
                .append("password","user1")
                .append("role","user")
                .append("firstname","firstname1")
                .append("lastname","lastname1")
                .append("checkedOutBooks",Arrays.asList()));
        collection.insertOne(new Document("username", "user2")
                .append("password","user2")
                .append("role","user")
                .append("firstname","firstname2")
                .append("lastname","lastname2")
                .append("checkedOutBooks",Arrays.asList()));
        collection.insertOne(new Document("username", "admin")
                .append("password","admin")
                .append("role","admin")
                .append("firstname","firstname3")
                .append("lastname","lastname3")
                .append("checkedOutBooks",Arrays.asList()));
    }
}
