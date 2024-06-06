
import com.mongodb.client.MongoCollection;
import database.DBConnection;
import doas.UserDAO;
import org.bson.Document;
import org.junit.Test;
import documents.User;

public class TestUserDAO {

    @Test
    public void testGetUser() {

        DBConnection connect = new DBConnection();
        connect.getCollection("BookTest").deleteMany(new Document());
        connect.getCollection("UserTest").deleteMany(new Document());
        SetUpTest.setUp();

        // DEFAULT USERNAME FOR TEST: user1
        // DEFAULT PASSWORD FOR TEST: user1
        String username = "user1";
        String password = "user1";

        MongoCollection<Document> user = connect.getCollection("UserTest");
        MongoCollection<Document> book = connect.getCollection("BookTest");
        UserDAO.initializeCollections(user, book);

        User newUser = UserDAO.getUser(username);
        System.out.println(newUser.getUserID() + " " + newUser.getFirstName() + " " +
                newUser.getLastName() + " " + newUser.getUsername());
    }

    @Test
    public void testLogginIn() {

        DBConnection connect = new DBConnection();
        connect.getCollection("BookTest").deleteMany(new Document());
        connect.getCollection("UserTest").deleteMany(new Document());
        SetUpTest.setUp();

        // DEFAULT USERNAME FOR TEST: user1
        // DEFAULT PASSWORD FOR TEST: user1
        String username = "user1";
        String password = "user1";

        MongoCollection<Document> user = connect.getCollection("UserTest");
        MongoCollection<Document> book = connect.getCollection("BookTest");
        UserDAO.initializeCollections(user, book);

        System.out.println(UserDAO.authenticateUser(username, password));
    }

    @Test
    public void testCreateNewUser() {

        DBConnection connect = new DBConnection();
        connect.getCollection("BookTest").deleteMany(new Document());
        connect.getCollection("UserTest").deleteMany(new Document());
        SetUpTest.setUp();

        // DEFAULT USERNAME FOR TEST: user1
        // DEFAULT PASSWORD FOR TEST: user1
        String username = "user1";
        String password = "user1";

        MongoCollection<Document> user = connect.getCollection("UserTest");
        MongoCollection<Document> book = connect.getCollection("BookTest");
        UserDAO.initializeCollections(user, book);

        System.out.println(UserDAO.createNewUser(username, password, "ant", "yoo"));
        System.out.println(UserDAO.createNewUser("newUser1", "newUser2", "ant", "yoo"));
    }

    @Test
    public void testGrabCheckedOutBooks() {

        DBConnection connect = new DBConnection();
        connect.getCollection("BookTest").deleteMany(new Document());
        connect.getCollection("UserTest").deleteMany(new Document());
        SetUpTest.setUp();

        // DEFAULT USERNAME FOR TEST: user1
        // DEFAULT PASSWORD FOR TEST: user1
        String username = "user1";
        String password = "user1";

        MongoCollection<Document> user = connect.getCollection("UserTest");
        MongoCollection<Document> book = connect.getCollection("BookTest");
        UserDAO.initializeCollections(user, book);

        // should be null
        System.out.println(UserDAO.grabCheckedOutBooks(username));

        // should return books
        System.out.println(UserDAO.grabCheckedOutBooks("user2"));

    }
}
