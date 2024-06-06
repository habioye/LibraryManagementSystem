
import com.mongodb.client.MongoCollection;
import connection.DBConnection;
import dao.UserDAO;
import org.bson.Document;
import org.junit.Test;
import entity.User;

import static org.junit.Assert.*;

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
        assertEquals("user1", newUser.getUsername());
        assertEquals("user1", newUser.getPassword());
        assertEquals("user", newUser.getRole());
        assertEquals("firstname1", newUser.getFirstName());
        assertEquals("lastname1", newUser.getLastName());
    }


    @Test
    public void testAuthenticateUser() {

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

        assertTrue(UserDAO.authenticateUser(username, password));
        assertFalse(UserDAO.authenticateUser("hello", password));
        assertFalse(UserDAO.authenticateUser(username, "hello"));
        assertFalse(UserDAO.authenticateUser("", ""));
        assertFalse(UserDAO.authenticateUser("", password));

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

        assertFalse(UserDAO.createNewUser(username, password, "ant", "yoo"));
        assertTrue(UserDAO.createNewUser("newUser1", "newUser1", "ant", "yoo"));
        assertTrue(UserDAO.createNewUser("newUser2", "newUser2", "ant", "yoo"));
        assertFalse(UserDAO.createNewUser("newUser2", "newUser2", "ant", "yoo"));
        assertTrue(UserDAO.createNewUser("newUser3", "newUser2", "ant", "yoo"));
        assertFalse(UserDAO.createNewUser("newUser3", "newUser2", "ant", "yoo"));
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
