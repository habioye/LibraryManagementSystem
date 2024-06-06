package doas;


import com.mongodb.BasicDBList;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import database.DBConnection;
import documents.Book;
import documents.User;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static MongoCollection<Document> userCollection;
    private static MongoCollection<Document> bookCollection;

    public static void initializeCollections(MongoCollection<Document> userCollec,
                                             MongoCollection<Document> bookCollec) {
        userCollection = userCollec;
        bookCollection = bookCollec;
    }

    public static boolean createNewUser(String username, String password, String first, String last) {

        if(userCollection == null || bookCollection == null) {
            System.out.println("ERROR: Class was not initialized!");
            return false;
        }

        // Check if user with that
        // username already exists.
        User newUser = getUser(username);
        if(newUser != null) {
            System.out.println("Error: Username is taken!");
            return false;
        }

        // Create new user
        Document newUserDoc = new Document()
                .append("username", username)
                .append("password", password)
                .append("role", "user")
                .append("firstname", first)
                .append("lastname", last)
                .append("checkedOutBooks", new BasicDBList());

        // Insert
        var result = userCollection.insertOne(newUserDoc);

        // If operation was successful, return true;
        if(result.wasAcknowledged())
            return true;
        // else return false
        return false;

    }

    public static User getUser(String username) {

        if(userCollection == null || bookCollection == null) {
            System.out.println("ERROR: Class was not initialized!");
            return null;
        }

        // Acquire all users with provided username.
        Document filter = new Document("username", username);
        FindIterable<Document> result = userCollection.find(filter);

        // Create user objects with that username.
        ArrayList<User> usersGrabbed = new ArrayList<>();
        for(Document doc: result) {
            List<ObjectId> checkedOutBooks = doc.getList("checkedOutBooks", ObjectId.class);
            ArrayList<String> checkOutBooks = new ArrayList<>();
            checkedOutBooks.forEach(book -> checkOutBooks.add(book.toString()));
            User user = new User(doc.get("_id").toString(), doc.get("role").toString(), doc.get("username").toString(),
                    doc.get("password").toString(), doc.get("firstname").toString(), doc.get("lastname").toString(),
                    checkOutBooks.toArray(new String[0]));
            usersGrabbed.add(user);
        }

        // If no user with that username, return null
        if(usersGrabbed.isEmpty())
            return null;

        // If multiple users with that username, throw an exception.
        if(usersGrabbed.size() > 1)
            throw new RuntimeException("SERVER ERROR: multiple users with the same username!");

        // Return user.
        return usersGrabbed.getFirst();

    }

    public static boolean authenticateUser(String username, String password) {

        if(userCollection == null || bookCollection == null) {
            System.out.println("ERROR: Class was not initialized!");
            return false;
        }

        // Acquire all users with provided username and password
        Bson filter = Filters.and(Filters.eq("username", username), Filters.eq("password", password));
        FindIterable<Document> result = userCollection.find(filter);

        // Create user objects with given credentials
        ArrayList<User> usersGrabbed = new ArrayList<>();
        for(Document doc: result) {
            List<String> checkedOutBooks = doc.getList("checkedOutBooks", String.class);
            User user = new User(doc.get("_id").toString(), doc.get("role").toString(), doc.get("username").toString(),
                    doc.get("password").toString(), doc.get("firstname").toString(), doc.get("lastname").toString(),
                    checkedOutBooks.toArray(new String[0]));
            usersGrabbed.add(user);
        }

        // If no user exists with those credentials
        // return false
        if(usersGrabbed.isEmpty())
            return false;

        // If multiple users exist with those credentials
        // throw runtime exception.
        if(usersGrabbed.size() > 1)
            throw new RuntimeException("SERVER ERROR: multiple users with that account in the database!");

        // Return true as there exists only
        // one user with those credentials.
        return true;

    }

    public static ArrayList<Book> grabCheckedOutBooks(String username) {

        if(userCollection == null || bookCollection == null) {
            System.out.println("ERROR: Class was not initialized!");
            return null;
        }

        // Gather the user making the request
        User currentUser = getUser(username);
        if(currentUser == null)
            return null;

        // Gather all the user's checked out books
        ArrayList<Book> userCheckOutBooks = new ArrayList<>();
        for(String bookID: currentUser.getCheckedOutBooks()) {

            // Create filter per bookID in user's array.
            Document filter = new Document("_id", new ObjectId(bookID));
            FindIterable<Document> books = bookCollection.find(filter);

            // Gather the book with given ID.
            ArrayList<Book> booksWithSameID = new ArrayList<>();
            for(Document doc: books) {
                List<String> genres = doc.getList("genres", String.class);
                boolean checkedOut = (boolean) doc.get("checkedOut");
                Book newBook = new Book(doc.get("_id").toString(), doc.get("title").toString(),
                        doc.get("description").toString(), doc.get("author").toString(), genres, checkedOut,
                        doc.get("currentTransactionId").toString());
                booksWithSameID.add(newBook);
            }

            // If no book exist with given ID
            if(booksWithSameID.isEmpty())
                throw new RuntimeException("SERVER ERROR: book does not exist in the database!");

            // If multiple books exist with same ID
            if(booksWithSameID.size() > 1)
                throw new RuntimeException("SERVER ERROR: book has multiple instances in the database!");

            // Add to list of checked out books
            userCheckOutBooks.add(booksWithSameID.getFirst());
        }

        // If user has not checked out books, return null
        if(userCheckOutBooks.isEmpty())
            return null;

        // Return all checked out books.
        return userCheckOutBooks;
    }

//    public static boolean checkoutBook(String bookID) {
//
//    }

}

//public boolean authenticateUser() {
//    while(true) {
//        ArrayList<Document> possibleMatches = new ArrayList<>();
//
//        System.out.println("\nEnter your username.\nEnter 'exit' to return to menu.");
//        System.out.print("> ");
//        String user = consoleReader.nextLine().strip().toLowerCase();
//        if(user.equals("exit")) return false;
//
//        System.out.println("\nEnter your password.\nEnter 'exit' to return to menu.");
//        System.out.print("> ");
//        String pass = consoleReader.nextLine().strip();
//        if(pass.equals("exit")) return false;
//
//        Bson filter  = Filters.and(Filters.eq("username", user), Filters.eq("password", pass));
//        userDocuments.find(filter).forEach(possibleMatches::add);
//
//        if(possibleMatches.size() > 1) {
//            System.out.println("Error: multiple users with that login info. Server issue must be resolved. Closing...");
//            System.exit(0);
//        }
//        else if(possibleMatches.isEmpty()) {
//            System.out.println("No user found with that username and password");
//        }
//        else {
//            Document doc = possibleMatches.get(0);
//            ArrayList<ObjectId> allPosts;
//            if(doc.get("posts") == null) allPosts = new ArrayList<>();
//            else allPosts = (ArrayList<ObjectId>) doc.get("posts");
//            currentUser = new User((ObjectId) doc.get("_id"), (String) doc.get("name"), allPosts);
//            return true;
//        }
//    }
//}